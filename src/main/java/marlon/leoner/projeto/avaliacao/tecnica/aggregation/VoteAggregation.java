package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.VoteDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateVoteParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.SessionException;
import marlon.leoner.projeto.avaliacao.tecnica.service.AssociateService;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;
import marlon.leoner.projeto.avaliacao.tecnica.service.VoteService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VoteAggregation {

    private static final String SESSION_IS_CLOSE = "A sessão para a pauta informada já foi finalizada.";
    private static final String USER_ALREADY_VOTE = "O associado informado já votou nesta sessão.";
    private static final String USER_NOT_ABLE_VOTE = "O associado informado não possui permissão para votar.";

    private final VoteService voteService;
    private final SessionService sessionService;
    private final AssociateService associateService;

    public List<VoteDTO> getAllVotes() {
        return voteService.getAllVotes()
                .stream()
                .map(Vote::toDto)
                .collect(Collectors.toList());
    }

    public VoteDTO getVote(String voteId) {
        Vote vote = voteService.getVoteOrExceptionById(voteId);
        return vote.toDto();
    }

    private Session getSession(String sessionId) {
        return sessionService.getSessionOrExceptionById(sessionId);
    }

    private Associate getAssociate(String associateId) {
        return associateService.getAssociateOrExceptionById(associateId);
    }

    public String createVote(CreateVoteParam params) {
        Session session = getSession(params.getSessionId());
        Associate associate = getAssociate(params.getAssociateId());

        this.validateSessionOpened(session);
        this.validateAssociateAbleToVote(associate);
        this.validateAssociateVoteInSession(session, associate);

        Vote vote = voteService.createVote(params, session, associate);
        return vote.getId();
    }

    private void validateSessionOpened(Session session) {
        if (!session.isOpened()) {
            throw new SessionException(SESSION_IS_CLOSE);
        }
    }

    private void validateAssociateAbleToVote(Associate associate) {
        if (!associate.isAbleToVote()) {
            throw new SessionException(USER_NOT_ABLE_VOTE);
        }
    }

    private void validateAssociateVoteInSession(Session session, Associate associate) {
        Optional<Vote> vote = session.getVoteByAssociateId(associate);
        if (vote.isPresent()) {
            throw new SessionException(USER_ALREADY_VOTE);
        }
    }
}
