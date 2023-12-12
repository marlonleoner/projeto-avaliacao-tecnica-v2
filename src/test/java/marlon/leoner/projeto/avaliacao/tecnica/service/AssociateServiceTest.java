package marlon.leoner.projeto.avaliacao.tecnica.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;

import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateAssociateParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.AssociateRepository;
import marlon.leoner.projeto.avaliacao.tecnica.service.AssociateService;

import marlon.leoner.projeto.avaliacao.tecnica.service.IntegrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AssociateServiceTest {

    private final static String ASSOCIATE_ID = "e6a7e9ea-1d2c-4e02-ad26-236d4560ab59";

    private AssociateService service;
    private AssociateRepository repository;
    private IntegrationService integrationService;

    private Associate associateMock;

    private CreateAssociateParam createAssociateParam;

    @BeforeEach
    void initService() {
        repository = mock(AssociateRepository.class);
        integrationService = mock(IntegrationService.class);
        service = new AssociateService(repository, integrationService);


        associateMock = new Associate();
        associateMock.setId(ASSOCIATE_ID);
        associateMock.setName("Associado 1");
        associateMock.setCpf("00011122233");
        associateMock.setAbleToVote(Boolean.TRUE);
        associateMock.setCreatedAt(new Date());

        createAssociateParam = new CreateAssociateParam();
        createAssociateParam.setCpf("00011122233");
        createAssociateParam.setName("Associado 1");
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar um associado.")
    void validarSucessoAoBuscarAssociado() {
        when(repository.findById(anyString())).thenReturn(Optional.of(associateMock));

        Associate associate = service.getAssociateOrExceptionById(anyString());
        assertEquals(associateMock, associate);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar um associado.")
    void validarErroAoBuscarAssociado() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.getAssociateOrExceptionById(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao validar um associado.")
    void validarSucessoAoValidarAssociado() {
        when(repository.findByCpf(anyString())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.validateAssociateExists(anyString()));
    }

    @Test
    @DisplayName("Validar ERRO ao validar um associado.")
    void validarErroAoValidarAssociado() {
        when(repository.findByCpf(anyString())).thenReturn(Optional.of(associateMock));

        assertThrows(ObjectAlreadyExistsException.class, () -> service.validateAssociateExists(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar um associado.")
    void validarSucessoAoCriarAssociado() {
        when(integrationService.isCpfValid(anyString())).thenReturn(Boolean.TRUE);

        service.createAssociate(createAssociateParam);

        verify(repository, atLeast(1)).save(any());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todos associados.")
    void validarSucessoAoBuscarTodosAssociados() {
        service.getAllAssociates();

        verify(repository, atLeast(1)).findAll();
    }
}
