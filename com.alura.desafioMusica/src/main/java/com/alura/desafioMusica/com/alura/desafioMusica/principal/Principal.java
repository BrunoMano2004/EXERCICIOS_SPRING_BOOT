package com.alura.desafioMusica.com.alura.desafioMusica.principal;

import com.alura.desafioMusica.com.alura.desafioMusica.model.ArtistaTO;
import com.alura.desafioMusica.com.alura.desafioMusica.model.MusicaTO;
import com.alura.desafioMusica.com.alura.desafioMusica.repository.ArtistaRepository;
import com.alura.desafioMusica.com.alura.desafioMusica.repository.MusicaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    ArtistaRepository artistaRepository;

    MusicaRepository musicaRepository;

    Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository artistaRepository, MusicaRepository musicaRepository){
        this.artistaRepository = artistaRepository;
        this.musicaRepository = musicaRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Cadastrar Artista
                    2 - Buscar Artista
                    3 - Cadastrar Música
                    4 - Buscar Música
                    5 - Listar musicas
                    6 - Listar por ordem de lançamento
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    buscarArtistaPeloNome();
                    break;
                case 3:
                    cadastrarMusica();
                    break;
                case 4:
                    buscarMusicaPeloNome();
                    break;
                case 5:
                    listarMusicas();
                    break;
                case 6:
                    listarMusicasPorLancamento();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public ArtistaTO validarArtistaPeloNome(String nome){
       Optional<ArtistaTO> artista = artistaRepository.buscarArtistaPeloNome(nome);

       if (artista.isPresent()){
           return artista.get();
       }
       System.out.println("Artista não encontrado!");
       exibeMenu();
       return null;
    }

    public void cadastrarArtista(){
        System.out.println("Digite o nome do artista: ");
        var nomeArtista = leitura.nextLine();

        System.out.println("Digite o tipo de artista(solo, dupla, banda): ");
        var tipoArtista = leitura.nextLine();

        System.out.println("Informe a data da primeira música lançada por ele/ela(dd/MM/yyyy): ");
        var dataMusicaLancada = leitura.nextLine();

        ArtistaTO artista = new ArtistaTO(nomeArtista, tipoArtista, dataMusicaLancada);
        artistaRepository.save(artista);
    }

    public void buscarArtistaPeloNome(){
        System.out.println("Digite o nome do artista");
        var nomeArtista = leitura.nextLine();

        Optional<ArtistaTO> artista = artistaRepository.buscarArtistaPeloNome(nomeArtista);

        if (artista.isPresent()){
            var artistaBuscado = artista.get();
            System.out.println("Nome: " + artistaBuscado.getNome() + "\n" +
                    "Tipo de artista: " + artistaBuscado.getTipo() + "\n" +
                    "Data primeira música: " + artistaBuscado.getDataPrimeiraMusica());
            System.out.println("Suas músicas: ");
            artistaBuscado.getMusicas().forEach(m -> System.out.println(m.getNome()));
        } else {
            System.out.println("Artista não encontrado!");
        }
    }

    public void cadastrarMusica(){
        System.out.println("Digite o nome da música: ");
        var nomeMusica = leitura.nextLine();

        System.out.println("Digite a duração da música: ");
        var duracaoMusica = leitura.nextDouble();
        leitura.nextLine();

        System.out.println("Digite o genero da música (pop, rock, eletrônica, outros): ");
        var generoMusical = leitura.nextLine();

        System.out.println("Digite a data de lançamento (dd/MM/yyyy):");
        var anoLancamento = leitura.nextLine();

        System.out.println("Digite o nome do artista criador: ");
        var nomeArtista = leitura.nextLine();

        var artistaBuscado = validarArtistaPeloNome(nomeArtista);

        MusicaTO musica = new MusicaTO(nomeMusica, duracaoMusica, generoMusical, anoLancamento, artistaBuscado);

        musicaRepository.save(musica);
    }

    public void buscarMusicaPeloNome(){
        System.out.println("Digite o nome da música: ");
        var nomeMusica = leitura.nextLine();

        Optional<MusicaTO> musica = musicaRepository.buscarMusicaPeloNome(nomeMusica);

        if (musica.isPresent()){
            var musicaBuscada = musica.get();
            System.out.println("Nome: " + musicaBuscada.getNome() + "\n" +
                                "Duração: " + musicaBuscada.getDuracao().toString().replace(".", ":") + " minutos\n" +
                                "Gênero musical: " + musicaBuscada.getGenero() + "\n" +
                                "Data lançamento: " + musicaBuscada.getDataLancamento() + "\n" +
                                "Artista criador: " + musicaBuscada.getArtista());
        } else {
            System.out.println("Música não encontrada!");
        }
    }

    public void listarMusicas(){

        List<MusicaTO> musicas = musicaRepository.findAll();

        musicas.forEach(System.out::println);
    }

    public void listarMusicasPorLancamento(){
        System.out.println("Digite o nome do artista: ");
        var nomeArtista = leitura.nextLine();

        ArtistaTO artista = validarArtistaPeloNome(nomeArtista);

        List<MusicaTO> musicas = musicaRepository.listarMusicasPorArtistasPorLancamento(artista);
        musicas.forEach(System.out::println);
    }
}
