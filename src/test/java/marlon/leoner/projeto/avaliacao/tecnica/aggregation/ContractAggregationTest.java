package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ContractDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.service.ContractService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContractAggregationTest {

    private ContractAggregation aggregation;

    private ContractService service;

    private Contract contractMock;

    private List<Contract> contractsMock;

    private Iterable<ContractDTO> contractsMockDTO;

    private CreateContractParam param;

    @BeforeEach
    void initService() {
        service = mock(ContractService.class);
        aggregation = new ContractAggregation(service);

        contractMock = generateContract("1");

        contractsMock = new ArrayList<>();
        contractsMock.add(generateContract("1"));
        contractsMock.add(generateContract("2"));
        contractsMock.add(generateContract("3"));

        contractsMockDTO = generateContractsDTO();

        param = new CreateContractParam();
        param.setName("Contract 1");
        param.setDescription("Description Contract 1");
    }

    private Contract generateContract(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setName("Contract " + id);
        contract.setSlug("contract-" + id);
        contract.setDescription("Description Contract " + id);
        contract.setCreatedAt(new Date());

        return contract;
    }

    private List<ContractDTO> generateContractsDTO() {
        return contractsMock.stream()
                .map(Contract::toDto)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todas as pautas.")
    void validarSucessoAoBuscarPautas() {
        when(service.getAllContracts()).thenReturn(contractsMock);

        Iterable<ContractDTO> contractsDTO = aggregation.getAllContracts();
        assertIterableEquals(contractsMockDTO, contractsDTO);
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar uma pauta.")
    void validarSucessoAoBuscarPauta() {
        when(service.getContractOrExceptionById(anyString())).thenReturn(contractMock);

        ContractDTO contractDTO = aggregation.getContract(anyString());
        assertEquals(contractMock.toDto(), contractDTO);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar uma pauta.")
    void validarErroAoBuscarPauta() {
        when(service.getContractOrExceptionById(anyString())).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> aggregation.getContract(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar uma pauta.")
    void validarSucessoAoCriarPauta() {
        when(service.createContract(any())).thenReturn(contractMock);

        String id = aggregation.createContract(param);

        assertEquals(contractMock.getId(), id);
    }

    @Test
    @DisplayName("Validar ERRO ao criar uma pauta.")
    void validarErroAoCriarPauta() {
        when(service.getContractBySlug(anyString())).thenReturn(Optional.of(contractMock));

        assertThrows(ObjectAlreadyExistsException.class, () -> aggregation.createContract(param));
    }
}
