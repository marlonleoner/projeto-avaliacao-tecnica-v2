package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import lombok.AllArgsConstructor;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ContractDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ContractAggregation {

    private final static String CONTRACT_ALREADY_EXISTS = "Já existe uma pauta cadastrada com este nome.";

    private final ContractService service;

    public List<ContractDTO> getAllContracts() {
        return service.getAllContracts()
                .stream()
                .map(Contract::toDto)
                .collect(Collectors.toList());
    }

    public ContractDTO getContract(String slug) {
        Contract contract = service.getContractOrExceptionBySlug(slug);

        return contract.toDto();
    }

    public String createContract(CreateContractParam params) {
        this.validateContractExists(params.getSlug());
        Contract contract = service.createContract(params);

        return contract.getSlug();
    }

    private void validateContractExists(String slug) {
        Optional<Contract> contract = service.getContractBySlug(slug);
        contract.ifPresent((_contract) -> {
            throw new ObjectAlreadyExistsException(CONTRACT_ALREADY_EXISTS);
        });
    }
}
