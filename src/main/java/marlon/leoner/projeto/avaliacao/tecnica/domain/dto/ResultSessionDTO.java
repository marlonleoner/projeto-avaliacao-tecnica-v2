package marlon.leoner.projeto.avaliacao.tecnica.domain.dto;

import lombok.Data;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class ResultSessionDTO {

    private String sessionId;

    private Long totalVotes;

    private Long yesVotes;

    private Long noVotes;

    private Double percentageYesVotes;

    private Double percentageNoVotes;

    public ResultSessionDTO(Session session) {
        this.sessionId = session.getId();
        List<Vote> votes = session.getVotes();
        this.totalVotes = (long) votes.size();
        this.yesVotes = votes.stream().filter(Vote::isYes).count();
        this.noVotes = votes.stream().filter(Vote::isNo).count();
        double pYesVotes = (this.yesVotes * 100.0d) / totalVotes;
        double pNoVotes = (this.noVotes  * 100.0d) / totalVotes;
        this.percentageYesVotes = BigDecimal.valueOf(pYesVotes).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.percentageNoVotes = BigDecimal.valueOf(pNoVotes).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
