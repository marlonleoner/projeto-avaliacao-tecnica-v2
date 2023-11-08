package marlon.leoner.projeto.avaliacao.tecnica.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteDTO {

    private String id;

    private SessionDTO session;

    private AssociateDTO associate;

    private String vote;
}
