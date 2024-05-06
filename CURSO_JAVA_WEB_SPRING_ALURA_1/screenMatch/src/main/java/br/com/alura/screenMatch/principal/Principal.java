package br.com.alura.screenMatch.principal;

import br.com.alura.screenMatch.model.DadosEpisodeo;
import br.com.alura.screenMatch.model.DadosSerie;
import br.com.alura.screenMatch.model.DadosTemporada;
import br.com.alura.screenMatch.model.Episodio;
import br.com.alura.screenMatch.service.ConsumoAPI;
import br.com.alura.screenMatch.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConverteDados converteDados = new ConverteDados();
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=1207e02b";

    public void exibirMenu() throws JsonProcessingException {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int x = 1; x <= dadosSerie.getTotalTemporadas(); x++){
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + x + API_KEY);
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.getEpisodeos().forEach(i -> System.out.println(i.getTitulo())));

        List<DadosEpisodeo> episodeos = temporadas.stream()
                .flatMap(t -> t.getEpisodeos().stream())
                .collect(Collectors.toList());

        System.out.println("Top 10 episódios: ");
        episodeos.stream()
                .filter(e -> !e.getAvaliacao().contentEquals("N/A"))
                .sorted(Comparator.comparing(DadosEpisodeo::getAvaliacao).reversed())
                .limit(10)
                .map(e -> e.getTitulo().toUpperCase())
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.getEpisodeos().stream()
                        .map(d -> new Episodio(t.getNumero(), d))
                        ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Dgite um trecho do título do episódio: ");
        var trechoTitulo = leitura.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado!");
        }

        System.out.println("A partir de que ano você deseja ver os espisódios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data Lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvalicao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvalicao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvalicao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvalicao));
        System.out.println();
        System.out.println("Média: " + est.getAverage());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Quantidade: " + est.getCount());
    }
}
