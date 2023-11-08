package marlon.leoner.projeto.avaliacao.tecnica.domain;

import lombok.*;

import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.VoteDTO;
import marlon.leoner.projeto.avaliacao.tecnica.enums.VoteTypeEnum;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Vote extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Enumerated(EnumType.STRING)
    private VoteTypeEnum value;

    public Boolean isYes() {
        return VoteTypeEnum.YES.equals(this.value);
    }

    public Boolean isNo() {
        return VoteTypeEnum.NO.equals(this.value);
    }

    public VoteDTO toDto() {
        VoteDTO dto = new VoteDTO();
        dto.setId(this.id);
        dto.setSession(session.toDto());
        dto.setAssociate(associate.toDto());
        dto.setVote(this.value.getDescription());

        return dto;
    }
}
