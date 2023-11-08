package marlon.leoner.projeto.avaliacao.tecnica.repository;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, String> {

    Optional<Associate> findByCpf(String cpf);
}
