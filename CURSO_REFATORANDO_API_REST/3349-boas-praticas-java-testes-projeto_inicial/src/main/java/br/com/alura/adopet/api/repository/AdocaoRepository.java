package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

    @Query("select count(a) from Adocao a where a.tutor.id = :idTutor and a.status = 'APROVADO'")
    int numeroDeAdocoesFeitasPorUmTutorComStatusAprovado(Long idTutor);

    boolean existsByTutorIdAndStatus(Long idTutor, StatusAdocao status);

}
