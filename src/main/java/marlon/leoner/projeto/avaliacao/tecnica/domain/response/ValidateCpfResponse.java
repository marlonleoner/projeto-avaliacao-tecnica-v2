package marlon.leoner.projeto.avaliacao.tecnica.domain.response;

import lombok.Data;

@Data
public class ValidateCpfResponse {

    private Boolean valid;

    private String formatted;
}
