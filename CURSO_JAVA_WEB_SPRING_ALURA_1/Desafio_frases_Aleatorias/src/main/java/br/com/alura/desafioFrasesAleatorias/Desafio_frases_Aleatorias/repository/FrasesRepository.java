package br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.repository;

import br.com.alura.desafioFrasesAleatorias.Desafio_frases_Aleatorias.model.FrasesTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrasesRepository extends JpaRepository<FrasesTO, Long> {
}
