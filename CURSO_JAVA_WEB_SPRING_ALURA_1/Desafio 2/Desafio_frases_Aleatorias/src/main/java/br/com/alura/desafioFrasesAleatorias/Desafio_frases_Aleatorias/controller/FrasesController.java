package br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.controller;

import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.dto.FraseDTO;
import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.model.FrasesTO;
import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.service.FrasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrasesController {

    @Autowired
    FrasesService frasesService;

    @GetMapping("/series/frases")
    public FraseDTO obterFraseAleatoria(){
        return frasesService.obterFraseAleatoria();
    }
}
