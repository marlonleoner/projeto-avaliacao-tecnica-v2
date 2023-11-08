package marlon.leoner.projeto.avaliacao.tecnica.repository;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    Optional<Contract> findBySlug(String slug);
}
