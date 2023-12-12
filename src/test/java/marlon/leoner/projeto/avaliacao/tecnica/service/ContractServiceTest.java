package marlon.leoner.projeto.avaliacao.tecnica.service;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.ContractRepository;
import marlon.leoner.projeto.avaliacao.tecnica.service.ContractService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContractServiceTest {

    private ContractService service;
    private ContractRepository repository;

    private Contract contractMock;

    private CreateContractParam createContractParam;

    @BeforeEach
    void initService() {
        repository = mock(ContractRepository.class);
        service = new ContractService(repository);

        contractMock = new Contract();
        contractMock.setId("0");
        contractMock.setName("Contract 1");
        contractMock.setSlug("contract-1");
        contractMock.setDescription("Description Contract 1");
        contractMock.setCreatedAt(new Date());

        createContractParam = new CreateContractParam();
        createContractParam.setName("Contract 1");
        createContractParam.setDescription("Description Contract 1");
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todas as pautas.")
    void validarSucessoAoBuscarTodasPautas() {
        service.getAllContracts();

        verify(repository, atLeast(1)).findAll();
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar uma pauta.")
    void validarSucessoAoBuscarPauta() {
        when(repository.findById(anyString())).thenReturn(Optional.of(contractMock));

        Contract contract = service.getContractOrExceptionById(anyString());
        assertEquals(contractMock, contract);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar uma pauta.")
    void validarErroAoBuscarPauta() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.getContractOrExceptionById(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar uma pauta.")
    void validarSucessoAoBuscarPautaBySlug() {
        when(repository.findBySlug(anyString())).thenReturn(Optional.of(contractMock));

        Optional<Contract> contractOptional = service.getContractBySlug(anyString());
        assertEquals(contractMock, contractOptional.get());
    }

    @Test
    @DisplayName("Validar ERRO ao buscar uma pauta.")
    void validarErroAoBuscarPautaBySlug() {
        when(repository.findBySlug(anyString())).thenReturn(Optional.empty());

        Optional<Contract> contractOptional = service.getContractBySlug(anyString());
        assertEquals(Optional.empty(), contractOptional);
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar uma pauta.")
    void validarSucessoAoCriarPauta() {
        service.createContract(createContractParam);

        verify(repository, atLeast(1)).save(any());
    }
}
