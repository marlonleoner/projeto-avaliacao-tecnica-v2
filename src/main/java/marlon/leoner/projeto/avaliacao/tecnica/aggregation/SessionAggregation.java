package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ResultSessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.SessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.SessionException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.service.ContractService;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SessionAggregation {

    private static final String SESSION_IS_OPEN = "A pauta informada já possui uma sessão em andamento.";
    private static final String RESULT_FAILED = "Só é possível visualizar o resultado após o encerramento da sessão.";

    private final SessionService sessionService;

    private final ContractService contractService;

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

    private void validateSessionExists(String contractSlug) {
        Optional<Session> sessionOptional = sessionService.getSessionByContractSlug(contractSlug);
        sessionOptional.ifPresent((session) -> {
            if (session.isOpened()) throw new ObjectAlreadyExistsException(SESSION_IS_OPEN);
        });
    }

    private Contract getContract(String contractSlug) {
        return contractService.getContractOrExceptionBySlug(contractSlug);
    }

    public String createSession(CreateSessionParam params) {
        this.validateSessionExists(params.getContractSlug());

        Date now = new Date();

        Session session = params.toEntity();
        session.setContract(this.getContract(params.getContractSlug()));
        session.setOpenedAt(now);
        session.setClosedAt(this.calculateClosedAt(now, session.getDuration()));
        sessionService.createSession(session);

        return session.getId();
    }

    private Date calculateClosedAt(Date now, Long duration) {
        return new Date(now.getTime() + (duration * 60 * 1000));
    }

    public ResultSessionDTO getResultSession(String sessionId) {
        Session session = sessionService.getSessionOrExceptionById(sessionId);
        if (session.isOpened()) {
            throw new SessionException(RESULT_FAILED);
        }

        return session.toResultDto();
    }
}
