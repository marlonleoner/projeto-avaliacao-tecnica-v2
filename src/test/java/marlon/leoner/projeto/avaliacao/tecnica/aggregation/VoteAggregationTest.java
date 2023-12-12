package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.VoteDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateVoteParam;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.enums.VoteTypeEnum;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.SessionException;
import marlon.leoner.projeto.avaliacao.tecnica.service.AssociateService;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class VoteAggregationTest {

    private VoteAggregation aggregation;

    private VoteService service;

    private SessionService sessionService;

    private AssociateService associateService;

    private Vote voteMock;

    private Session createdSessionMock;
    private Session openSessionMock;
    private Session closedSessionMock;
    private Session notifiedSessionMock;

    private Associate associateMock;
    private Associate associateVoteMock;

    private List<Vote> votesMock;

    private Iterable<VoteDTO> votesMockDTO;

    private CreateVoteParam param;

    @BeforeEach
    void initService() {
        service = mock(VoteService.class);
        sessionService = mock(SessionService.class);
        associateService = mock(AssociateService.class);
        aggregation = new VoteAggregation(service, sessionService, associateService);

        createdSessionMock = generateSession(StatusSessionEnum.CREATED);
        openSessionMock = generateSession(StatusSessionEnum.OPEN);
        closedSessionMock = generateSession(StatusSessionEnum.CLOSED);
        notifiedSessionMock = generateSession(StatusSessionEnum.NOTIFIED);

        associateMock = generateAssociate(Boolean.FALSE);
        associateVoteMock = generateAssociate(Boolean.TRUE);

        voteMock = generateVote("1");

        votesMock = new ArrayList<>();
        votesMock.add(generateVote("1"));
        votesMock.add(generateVote("2"));
        votesMock.add(generateVote("3"));

        votesMockDTO = generateVoteDTOS();

        param = new CreateVoteParam();
        param.setVote("S");
        param.setSessionId("SESSION_ID");
        param.setAssociateId("ASSOCIATE_ID");
    }

    private Session generateSession(StatusSessionEnum status) {
        Session session = new Session();
        session.setContract(new Contract());
        session.setStatus(status);
        session.setVotes(new ArrayList<>());

        return session;
    }

    private Associate generateAssociate(Boolean ableToVote) {
        Associate associate = new Associate();
        associate.setCpf("00011122233");
        associate.setAbleToVote(ableToVote);

        return associate;
    }

    private Vote generateVote(String id) {
        Vote vote = new Vote();
        vote.setId(id);
        vote.setSession(createdSessionMock);
        vote.setAssociate(associateMock);
        vote.setValue(VoteTypeEnum.YES);
        vote.setCreatedAt(new Date());

        return vote;
    }

    private List<VoteDTO> generateVoteDTOS() {
        return votesMock.stream()
                .map(Vote::toDto)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todos os votos..")
    void validarSucessoAoBuscarVotos() {
        when(service.getAllVotes()).thenReturn(votesMock);

        Iterable<VoteDTO> votesDTO = aggregation.getAllVotes();
        assertIterableEquals(votesMockDTO, votesDTO);
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar um voto.")
    void validarSucessoAoBuscarVoto() {
        when(service.getVoteOrExceptionById(anyString())).thenReturn(voteMock);

        VoteDTO voteDTO = aggregation.getVote(anyString());
        assertEquals(voteMock.toDto(), voteDTO);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar um voto.")
    void validarErroAoBuscarVoto() {
        when(service.getVoteOrExceptionById(anyString())).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> aggregation.getVote(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar um voto.")
    void validarSucessoAoCriarVoto() {
        when(sessionService.getSessionOrExceptionById(anyString())).thenReturn(openSessionMock);
        when(associateService.getAssociateOrExceptionById(anyString())).thenReturn(associateVoteMock);
        when(service.createVote(any(), any(), any())).thenReturn(voteMock);

        String id = aggregation.createVote(param);

        assertEquals(voteMock.getId(), id);
    }

    @Test
    @DisplayName("Validar ERRO ao criar um voto - sessão criada.")
    void validarErroAoCriarVotoSessaoCriada() {
        when(sessionService.getSessionOrExceptionById(anyString())).thenReturn(createdSessionMock);

        assertThrows(SessionException.class, () -> aggregation.createVote(param));
    }

    @Test
    @DisplayName("Validar ERRO ao criar um voto - sessão fechada.")
    void validarErroAoCriarVotoSessaoFechada() {
        when(sessionService.getSessionOrExceptionById(anyString())).thenReturn(closedSessionMock);

        assertThrows(SessionException.class, () -> aggregation.createVote(param));
    }

    @Test
    @DisplayName("Validar ERRO ao criar um voto - sessão notificada.")
    void validarErroAoCriarVotoSessaoNotificada() {
        when(sessionService.getSessionOrExceptionById(anyString())).thenReturn(notifiedSessionMock);

        assertThrows(SessionException.class, () -> aggregation.createVote(param));
    }

    @Test
    @DisplayName("Validar ERRO ao criar um voto - associado impedido de votar.")
    void validarErroAoCriarVotoAssociadoImpedido() {
        when(sessionService.getSessionOrExceptionById(anyString())).thenReturn(openSessionMock);
        when(associateService.getAssociateOrExceptionById(anyString())).thenReturn(associateMock);

        assertThrows(SessionException.class, () -> aggregation.createVote(param));
    }
}
