package marlon.leoner.projeto.avaliacao.tecnica.domain;

import lombok.*;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ResultSessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.SessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session extends BaseEntity {

    @Column(name = "opened_at")
    private Date openedAt;

    @Column(name = "closed_at")
    private Date closedAt;

    private Long duration;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    private StatusSessionEnum status;

    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER)
    private List<Vote> votes;

    public boolean isCreated() {
        return status.isCreated();
    }

    public boolean isOpened() {
        return status.isOpened();
    }

    public boolean allowOpen() {
        return this.isCreated() && this.openedAt.before(new Date());
    }

    public boolean allowClose() {
        return this.isOpened() && this.closedAt.before(new Date());
    }

    public boolean allowResult() {
        return status.isClosed() || status.isNotified();
    }

    public SessionDTO toDto() {
        SessionDTO dto = new SessionDTO();
        dto.setId(this.id);
        dto.setContract(this.contract.toDto());
        dto.setOpenedAt(this.openedAt);
        dto.setClosedAt(this.closedAt);
        dto.setStatus(this.status.getDescription());
        dto.setTotalVotes((long) this.votes.size());

        return dto;
    }

    public Optional<Vote> getVoteByAssociateId(Associate associate) {
        for (Vote vote : this.votes) {
            if (vote.getAssociate().getId().equals(associate.getId())) {
                return Optional.of(vote);
            }
        }

        return Optional.empty();
    }

    public ResultSessionDTO toResultDto() {
        return new ResultSessionDTO(this);
    }

    public String getResult() {
        ResultSessionDTO result = this.toResultDto();

        return "A pauta " + this.contract.getName() + " (sessão " + this.id + ") foi " + result.getLabel() +
                ". A opção 'SIM' recebeu " + result.getPercentageYesVotes() + "% dos votos e a opção 'NÃO' recebeu " +
                result.getPercentageNoVotes() + "% dos votos. A sessão contou com a participação de " +
                result.getTotalVotes() + " associado(s).";
    }
}
