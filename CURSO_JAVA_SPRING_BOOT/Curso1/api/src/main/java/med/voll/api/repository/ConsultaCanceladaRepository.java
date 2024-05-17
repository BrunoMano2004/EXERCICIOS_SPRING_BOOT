package med.voll.api.repository;

import med.voll.api.domain.consultaModel.ConsultaCancelada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaCanceladaRepository extends JpaRepository<ConsultaCancelada, Long> {
}
