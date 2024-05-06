package br.com.alura.screenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosEpisodeo {

    @JsonAlias("Title")
    private String titulo;

    @JsonAlias("Episode")
    private Integer numero;

    @JsonAlias("imdbRating")
    private String avaliacao;

    @JsonAlias("Released")
    private String dataLancamento;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "DadosEpisodeo{" +
                "titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", avaliacao='" + avaliacao + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                '}';
    }
}
