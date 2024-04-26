package br.com.alura.screenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosSerie {

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("totalSeasons")
    private Integer totalTemporadas;

    @JsonAlias("imdbRating")
    private String avaliacao;
}
