package marlon.leoner.projeto.avaliacao.tecnica.service;

import lombok.AllArgsConstructor;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateVoteParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VoteService {

    private static final String VOTE_NOT_FOUND = "Não encontramos o voto informado.";

    private final VoteRepository repository;

    private void save(Vote vote) {
        repository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return repository.findAll();
    }

    public Optional<Vote> getVoteById(String voteId) {
        return repository.findById(voteId);
    }

    public Vote getVoteOrExceptionById(String voteId) {
        Optional<Vote> vote = this.getVoteById(voteId);
        return vote.orElseThrow(() -> new ObjectNotFoundException(VOTE_NOT_FOUND));
    }

    public Vote createVote(CreateVoteParam params, Session session, Associate associate) {
        Vote vote = params.toEntity();
        vote.setSession(session);
        vote.setAssociate(associate);

        this.save(vote);

        return vote;
    }
}
