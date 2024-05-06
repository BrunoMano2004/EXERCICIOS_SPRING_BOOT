package com.alura.desafioMusica.com.alura.desafioMusica.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Entity
@Table(name = "artistas")
public class ArtistaTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria tipo;

    private LocalDate dataPrimeiraMusica;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MusicaTO> musicas;

    @Transient
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ArtistaTO() {
    }

    public ArtistaTO(String nome, String categoria, String dataPrimeiraMusica){
        this.nome = nome;
        this.tipo = Categoria.fromString(categoria);

        try {
            this.dataPrimeiraMusica = LocalDate.parse(dataPrimeiraMusica, formatter);
        } catch (DateTimeParseException ex) {
            this.dataPrimeiraMusica = null;
        }
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

    public Categoria getTipo() {
        return tipo;
    }

    public void setTipo(Categoria tipo) {
        this.tipo = tipo;
    }

    public String getDataPrimeiraMusica() {
        return dataPrimeiraMusica.format(formatter);
    }

    public void setDataPrimeiraMusica(LocalDate dataPrimeiraMusica) {
        this.dataPrimeiraMusica = dataPrimeiraMusica;
    }

    public List<MusicaTO> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<MusicaTO> musicas) {
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return "Artista: " +
                " nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", dataPrimeiraMusica=" + dataPrimeiraMusica +
                '}';
    }
}
