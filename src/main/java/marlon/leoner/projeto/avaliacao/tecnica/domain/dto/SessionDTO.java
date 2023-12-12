package marlon.leoner.projeto.avaliacao.tecnica.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SessionDTO {

    private String id;

    private Date openedAt;

    private Date closedAt;

    private String status;

    private ContractDTO contract;

    private Long totalVotes;
}
