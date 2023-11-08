package marlon.leoner.projeto.avaliacao.tecnica.domain;

import lombok.*;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.AssociateDTO;
import marlon.leoner.projeto.avaliacao.tecnica.utils.ParseUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Associate extends BaseEntity {

    private String name;

    private String cpf;

    @Setter
    @Column(name = "able_to_vote")
    private Boolean ableToVote;

    public Boolean isAbleToVote() {
        return ableToVote;
    }

    public AssociateDTO toDto() {
        AssociateDTO dto = new AssociateDTO();
        dto.setId(this.id);
        dto.setCpf(ParseUtils.maskCpf(this.cpf));
        dto.setName(this.name);

        return dto;
    }
}
