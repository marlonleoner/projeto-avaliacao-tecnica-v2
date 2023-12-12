package marlon.leoner.projeto.avaliacao.tecnica.service;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateVoteParam;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.enums.VoteTypeEnum;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.SessionRepository;
import marlon.leoner.projeto.avaliacao.tecnica.repository.VoteRepository;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;
import marlon.leoner.projeto.avaliacao.tecnica.service.VoteService;
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
public class VoteServiceTest {

    private VoteService service;
    private VoteRepository repository;

    private Vote voteMock;

    private Session sessionMock;

    private Associate associateMock;

    private CreateVoteParam createVoteParam;

    @BeforeEach
    void initService() {
        repository = mock(VoteRepository.class);
        service = new VoteService(repository);

        sessionMock = new Session();

        associateMock = new Associate();

        voteMock = new Vote();
        voteMock.setId("ID");
        voteMock.setValue(VoteTypeEnum.YES);
        voteMock.setCreatedAt(new Date());
        voteMock.setSession(sessionMock);
        voteMock.setAssociate(associateMock);

        createVoteParam = new CreateVoteParam();
        createVoteParam.setVote("S");
        createVoteParam.setSessionId("SESSION_ID");
        createVoteParam.setAssociateId("ASSOCIATE_ID");
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todas os votos.")
    void validarSucessoAoBuscarTodosVotos() {
        service.getAllVotes();

        verify(repository, atLeast(1)).findAll();
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar um voto.")
    void validarSucessoAoBuscarVoto() {
        when(repository.findById(anyString())).thenReturn(Optional.of(voteMock));

        Vote vote = service.getVoteOrExceptionById(anyString());
        assertEquals(voteMock, vote);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar um voto.")
    void validarErroAoBuscarVoto() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.getVoteOrExceptionById(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar um voto.")
    void validarSucessoAoCriarSessao() {
        service.createVote(createVoteParam, sessionMock, associateMock);

        verify(repository, atLeast(1)).save(any());
    }
}
