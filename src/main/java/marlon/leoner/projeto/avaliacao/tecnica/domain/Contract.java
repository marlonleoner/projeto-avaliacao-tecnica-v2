package marlon.leoner.projeto.avaliacao.tecnica.domain;

import lombok.*;

import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ContractDTO;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contract extends BaseEntity {

    private String slug;

    private String name;

    private String description;

    public ContractDTO toDto() {
        ContractDTO dto = new ContractDTO();
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setSlug(this.getSlug());

        return dto;
    }
}
