package com.alura.desafioMusica.com.alura.desafioMusica.repository;

import com.alura.desafioMusica.com.alura.desafioMusica.model.ArtistaTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistaRepository extends JpaRepository<ArtistaTO, Long> {

    @Query("SELECT a FROM ArtistaTO a WHERE a.nome ILIKE %:nomeArtista%")
    Optional<ArtistaTO> buscarArtistaPeloNome(String nomeArtista);
}
