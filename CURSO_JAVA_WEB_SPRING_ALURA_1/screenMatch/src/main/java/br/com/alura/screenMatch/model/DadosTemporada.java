package br.com.alura.screenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosTemporada {

    @JsonAlias("Season")
    private Integer numero;

    @JsonAlias("Episodes")
    private List<DadosEpisodeo> episodeos;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<DadosEpisodeo> getEpisodeos() {
        return episodeos;
    }

    public void setEpisodeos(List<DadosEpisodeo> episodeos) {
        this.episodeos = episodeos;
    }

    @Override
    public String toString() {
        return "DadosTemporada{" +
                "numero=" + numero +
                ", episodeos=" + episodeos +
                '}';
    }
}
