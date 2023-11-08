package marlon.leoner.projeto.avaliacao.tecnica.repository;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {

    Optional<Vote> findByAssociateId(String associateId);

    Optional<Vote> findBySessionIdAndAssociateId(String sessionId, String associateId);
}
