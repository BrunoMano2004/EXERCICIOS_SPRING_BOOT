package med.voll.api.repository;

import med.voll.api.domain.medicoModel.Medico;
import med.voll.api.domain.pacienteModel.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select p.ativo
            from Paciente p
            where
            p.id = :idPaciente
            """)
    boolean findAtivoById(Long idPaciente);

}
