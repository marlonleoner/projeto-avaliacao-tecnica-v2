package marlon.leoner.projeto.avaliacao.tecnica.service;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.SessionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionService {

    private static final String SESSION_NOT_FOUND = "A sessão informada não foi encontrada.";

    private final SessionRepository repository;

    public List<Session> getAllSessions() {
        return repository.findAll();
    }

    public Optional<Session> getSessionById(String id) {
        return repository.findById(id);
    }

    public Optional<Session> getSessionByContractId(String contractId) {
        return repository.findByContractId(contractId);
    }

    private List<Session> getSessionsByStatus(StatusSessionEnum status) {
        return repository.findByStatus(status);
    }

    public List<Session> getCreatedSessions() {
        return this.getSessionsByStatus(StatusSessionEnum.CREATED);
    }

    public List<Session> getOpenSessions() {
        return this.getSessionsByStatus(StatusSessionEnum.OPEN);
    }

    public List<Session> getClosedSessions() {
        return this.getSessionsByStatus(StatusSessionEnum.CLOSED);
    }

    public void save(Session session) {
        repository.save(session);
    }

    public Session getSessionOrExceptionById(String id) {
        Optional<Session> session = this.getSessionById(id);
        return session.orElseThrow(() -> new ObjectNotFoundException(SESSION_NOT_FOUND));
    }

    public void createSession(Session session) {
        this.save(session);
    }
}
