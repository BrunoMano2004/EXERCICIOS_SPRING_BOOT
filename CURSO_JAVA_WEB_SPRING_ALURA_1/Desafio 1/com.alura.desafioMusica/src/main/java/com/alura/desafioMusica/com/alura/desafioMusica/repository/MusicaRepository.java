package com.alura.desafioMusica.com.alura.desafioMusica.repository;

import com.alura.desafioMusica.com.alura.desafioMusica.model.ArtistaTO;
import com.alura.desafioMusica.com.alura.desafioMusica.model.MusicaTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaTO, Long> {

    @Query("SELECT m FROM MusicaTO m WHERE m.nome ILIKE %:nomeMusica%")
    Optional<MusicaTO> buscarMusicaPeloNome(String nomeMusica);

    @Query("SELECT m FROM MusicaTO m WHERE m.artista = :artista ORDER BY YEAR(m.dataLancamento) DESC")
    List<MusicaTO> listarMusicasPorArtistasPorLancamento(ArtistaTO artista);
}
