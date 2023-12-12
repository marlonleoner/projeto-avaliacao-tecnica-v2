package marlon.leoner.projeto.avaliacao.tecnica.repository;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findByContractId(String contractId);
}
