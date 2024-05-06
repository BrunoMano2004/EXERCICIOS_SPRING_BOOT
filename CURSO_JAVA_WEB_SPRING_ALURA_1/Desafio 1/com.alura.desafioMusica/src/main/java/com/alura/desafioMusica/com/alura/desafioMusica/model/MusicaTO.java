package com.alura.desafioMusica.com.alura.desafioMusica.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "musicas")
public class MusicaTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double duracao;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private LocalDate dataLancamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ArtistaTO artista;

    @Transient
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MusicaTO() {
    }

    public MusicaTO(String nome, Double duracao, String genero, String dataLancamento, ArtistaTO artista) {
        this.nome = nome;
        this.duracao = duracao;
        this.genero = Genero.fromString(genero);

        try {
            this.dataLancamento = LocalDate.parse(dataLancamento, formatter);
        } catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

        this.artista = artista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getDataLancamento() {
        return dataLancamento.format(formatter);
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public ArtistaTO getArtista() {
        return artista;
    }

    public void setArtista(ArtistaTO artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Musica: " +
                " nome= '" + nome + '\'' +
                ", duracao= " + duracao +
                ", genero= " + genero +
                ", dataLancamento= " + dataLancamento +
                ", criador= " + artista.getNome() +
                '}';
    }
}
