package marlon.leoner.projeto.avaliacao.tecnica.service;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.SessionRepository;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SessionServiceTest {

    private SessionService service;
    private SessionRepository repository;

    private Session sessionMock;

    private CreateSessionParam createSessionParam;

    @BeforeEach
    void initService() {
        repository = mock(SessionRepository.class);
        service = new SessionService(repository);

        Contract contract = new Contract();
        contract.setId("CONTRACT_ID");

        sessionMock = new Session();
        sessionMock.setId("0");
        sessionMock.setOpenedAt(new Date());
        sessionMock.setClosedAt(new Date());
        sessionMock.setDuration(60L);
        sessionMock.setCreatedAt(new Date());
        sessionMock.setStatus(StatusSessionEnum.CREATED);
        sessionMock.setVotes(new ArrayList<>());
        sessionMock.setContract(contract);

        createSessionParam = new CreateSessionParam();
        createSessionParam.setContractId("CONTRACT_ID");
        createSessionParam.setStartDate(new Date());
        createSessionParam.setDuration(1L);
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todas as sessões.")
    void validarSucessoAoBuscarTodasSessoes() {
        service.getAllSessions();

        verify(repository, atLeast(1)).findAll();
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar uma sessão.")
    void validarSucessoAoBuscarSessao() {
        when(repository.findById(anyString())).thenReturn(Optional.of(sessionMock));

        Session session = service.getSessionOrExceptionById(anyString());
        assertEquals(sessionMock, session);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar uma sessão.")
    void validarErroAoBuscarSessao() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.getSessionOrExceptionById(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar uma sessão por id da pauta.")
    void validarSucessoAoBuscarSessaoByPauta() {
        when(repository.findByContractId(anyString())).thenReturn(Optional.of(sessionMock));

        Optional<Session> session = service.getSessionByContractId(anyString());
        assertEquals(sessionMock, session.get());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar sessões criadas.")
    void validarSucessoAoBuscarSessoesCriadas() {
        service.getCreatedSessions();

        verify(repository, atLeast(1)).findByStatus(any());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar sessões abertas.")
    void validarSucessoAoBuscarSessoesAbertas() {
        service.getOpenSessions();

        verify(repository, atLeast(1)).findByStatus(any());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar sessões fechadas.")
    void validarSucessoAoBuscarSessoesFechadas() {
        service.getClosedSessions();

        verify(repository, atLeast(1)).findByStatus(any());
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar uma sessão.")
    void validarSucessoAoCriarSessao() {
        service.createSession(createSessionParam.toEntity());

        verify(repository, atLeast(1)).save(any());
    }
}
