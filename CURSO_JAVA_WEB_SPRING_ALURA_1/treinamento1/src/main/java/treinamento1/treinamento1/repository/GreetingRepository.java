package treinamento1.treinamento1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import treinamento1.treinamento1.model.GreetingTO;

@Repository
public interface GreetingRepository extends JpaRepository<GreetingTO, Long> {
}
