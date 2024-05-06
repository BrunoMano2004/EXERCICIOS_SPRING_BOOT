package br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.service;

import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.dto.FraseDTO;
import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.model.FrasesTO;
import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.repository.FrasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FrasesService {

    @Autowired
    FrasesRepository frasesRepository;

    public FraseDTO obterFraseAleatoria() {
        FrasesTO frase = frasesRepository.buscarFraseAleatoria();
        return new FraseDTO(frase.getTitulo(), frase.getFrase(), frase.getPersonagem(), frase.getPoster());
    }
}
