package br.com.alura.screenMatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {

    private Integer temporada;

    private String titulo;

    private Integer numero;

    private Double avalicao;

    private LocalDate dataLancamento;

    public Episodio() {
        super();
    }

    public Episodio(Integer numeroTemporada, DadosEpisodeo dadosEpisodeo){
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodeo.getTitulo();
        this.numero = dadosEpisodeo.getNumero();

        try {
            this.avalicao = Double.valueOf(dadosEpisodeo.getAvaliacao());
            this.dataLancamento = LocalDate.parse(dadosEpisodeo.getDataLancamento());
        }catch (NumberFormatException e){
            this.avalicao = 0.0;
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    public Episodio(Integer temporada, String titulo, Integer numero, Double avalicao, LocalDate dataLancamento) {
        this.temporada = temporada;
        this.titulo = titulo;
        this.numero = numero;
        this.avalicao = avalicao;
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

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

    public Double getAvalicao() {
        return avalicao;
    }

    public void setAvalicao(Double avalicao) {
        this.avalicao = avalicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", avalicao=" + avalicao +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}
