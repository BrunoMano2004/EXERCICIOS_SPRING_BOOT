package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    private SerieRepository serieRepository;
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private List<Serie> series = new ArrayList<>();

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar série por ator
                    6 - Buscar top 5 séries
                    7 - Buscar séries por categoria
                    8 - Buscar série por max de temporadas
                    9 - Buscar episódios por trecho
                    10 - Buscar top 5 episódios
                    11 - Buscar episódios a partir de uma data
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriePorNumeroDeTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    buscarTop5Episodios();
                    break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        serieRepository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        listarSeriesBuscadas();
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()){

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas(){
        series = serieRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo(){
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent())
            System.out.println("Dados da série: " + serieBuscada.get());
        else
            System.out.println("Serie não encontrada!");
    }

    private void buscarSeriePorAtor(){
        System.out.println("Digite o nome do ator: ");
        var nomeAtor = leitura.nextLine();

        System.out.println("A partir de qual avaliação?");
        var avaliacao = leitura.nextDouble();

        Optional<List<Serie>> serieBuscada = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);

        if (serieBuscada.isPresent()){
            serieBuscada.get().stream()
                    .sorted(Comparator.comparing(Serie::getAtores))
                    .forEach(s -> System.out.println(s.getTitulo() + ": " + s.getAvaliacao()));
        } else {
            System.out.println("Ator não encontrado!");
        }
    }

    private void buscarTop5Series(){
        List<Serie> seriesBuscadas = serieRepository.findTop5ByOrderByAvaliacaoDesc();

        seriesBuscadas.forEach(s -> System.out.println(s.getTitulo() + ": " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria(){
        System.out.println("Digite a categoria desejada: ");
        var nomeGenero = leitura.nextLine();

        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesBuscadas = serieRepository.findByGenero(categoria);

        System.out.println("Séries na categoria " + nomeGenero + ": ");

        seriesBuscadas.forEach(System.out::println);
    }

    private void buscarSeriePorNumeroDeTemporadaEAvaliacao(){
        System.out.println("Digite o número máximo de temporadas: ");
        var numTemp = leitura.nextInt();

        System.out.println("Digite a avaliação desejada: ");
        var numAvali = leitura.nextDouble();

        List<Serie> seriesBuscadas = serieRepository.encontrarSerioPorTemporadaEAvaliacao(numTemp, numAvali);

        seriesBuscadas.forEach(System.out::println);
    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Digite o trecho do episodio: ");
        var nomeEpisodio = leitura.nextLine();

        List<Episodio> episodios = serieRepository.buscarEpisodioPorTrecho(nomeEpisodio);

        episodios.forEach(System.out::println);
    }

    private void buscarTop5Episodios(){
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepository.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e -> System.out.println(e.getTitulo() + ": " + e.getAvaliacao()));
        }
    }

    private void buscarEpisodioDepoisDeUmaData(){
        buscarSeriePorTitulo();

        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            System.out.println("Digite o ano limite de lançamento: ");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = serieRepository.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}