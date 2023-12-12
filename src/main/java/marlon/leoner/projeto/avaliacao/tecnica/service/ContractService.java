package marlon.leoner.projeto.avaliacao.tecnica.service;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.ContractRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ContractService {

    private static final String CONTRACT_NOT_FOUND = "Não foi possível encontrar a pauta desejada.";

    private final ContractRepository repository;

    public List<Contract> getAllContracts() {
        return repository.findAll();
    }

    public Optional<Contract> getContractById(String id) {
        return repository.findById(id);
    }

    public Optional<Contract> getContractBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    private void save(Contract contract) {
        repository.save(contract);
    }

    public Contract getContractOrExceptionById(String id) {
        Optional<Contract> contract = this.getContractById(id);
        return contract.orElseThrow(() -> new ObjectNotFoundException(CONTRACT_NOT_FOUND));
    }

    public Contract createContract(CreateContractParam params) {
        Contract contract = params.toEntity();
        this.save(contract);

        return contract;
    }
}
