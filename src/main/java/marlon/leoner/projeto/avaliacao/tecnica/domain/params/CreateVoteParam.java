package marlon.leoner.projeto.avaliacao.tecnica.domain.params;

import lombok.Data;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Vote;
import marlon.leoner.projeto.avaliacao.tecnica.enums.VoteTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateVoteParam {

    @NotNull(message = "O campo 'sessionId' é obrigatório.")
    private String sessionId;

    @NotNull(message = "O campo 'associateId' é obrigatório.")
    private String associateId;

    @NotNull(message = "O campo 'vote' é obrigatório.")
    @Pattern(regexp = "^[SN]", message = "Os valores para o campo 'vote' são: S ou N")
    private String vote;

    public Vote toEntity() {
        Vote entity = new Vote();
        entity.setValue(VoteTypeEnum.getInstance(this.vote));

        return entity;
    }
}
