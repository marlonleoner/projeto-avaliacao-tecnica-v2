package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ResultSessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.SessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.exception.SessionException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.service.ContractService;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;

import marlon.leoner.projeto.avaliacao.tecnica.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SessionAggregation {

    private static final String SESSION_IS_OPEN = "A pauta informada já possui uma sessão em andamento.";
    private static final String RESULT_FAILED = "Só é possível visualizar o resultado após o encerramento da sessão.";

    private final SessionService sessionService;

    private final ContractService contractService;

    private final RabbitTemplate rabbitMQTemplate;

    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions()
                .stream()
                .map(Session::toDto)
                .collect(Collectors.toList());
    }

    public SessionDTO getSession(String id) {
        Session session = sessionService.getSessionOrExceptionById(id);
        return session.toDto();
    }

    private void validateSessionExists(String contractId) {
        Optional<Session> sessionOptional = sessionService.getSessionByContractId(contractId);
        sessionOptional.ifPresent((session) -> {
            if (session.isOpened()) throw new ObjectAlreadyExistsException(SESSION_IS_OPEN);
        });
    }

    private Contract getContract(String id) {
        return contractService.getContractOrExceptionById(id);
    }

    public String createSession(CreateSessionParam params) {
        this.validateSessionExists(params.getContractId());

        Session session = params.toEntity();
        session.setContract(this.getContract(params.getContractId()));
        session.setClosedAt(this.calculateClosedAt(session.getOpenedAt(), session.getDuration()));
        session.setStatus(StatusSessionEnum.CREATED);
        sessionService.createSession(session);

        return session.getId();
    }

    private Date calculateClosedAt(Date openedAt, Long duration) {
        return new Date(openedAt.getTime() + (duration * 60 * 1000));
    }

    public ResultSessionDTO getResultSession(String sessionId) {
        Session session = sessionService.getSessionOrExceptionById(sessionId);
        if (!session.allowResult()) {
            throw new SessionException(RESULT_FAILED);
        }

        return session.toResultDto();
    }

    public void openSessions() {
        List<Session> sessions = sessionService.getCreatedSessions();
        sessions.forEach(session -> {
            rabbitMQTemplate.convertAndSend(Constants.OPEN_SESSION_QUEUE, session.getId());
        });
    }

    public void closeSessions() {
        List<Session> sessions = sessionService.getOpenSessions();
        sessions.forEach(session -> {
            rabbitMQTemplate.convertAndSend(Constants.CLOSE_SESSION_QUEUE, session.getId());
        });
    }

    public void notifySessions() {
        List<Session> sessions = sessionService.getClosedSessions();
        sessions.forEach(session -> {
            rabbitMQTemplate.convertAndSend(Constants.NOTIFY_SESSION_RESULT_QUEUE, session.getId());
        });
    }
}
