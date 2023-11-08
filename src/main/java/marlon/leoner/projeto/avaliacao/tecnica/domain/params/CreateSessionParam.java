package marlon.leoner.projeto.avaliacao.tecnica.domain.params;

import lombok.Data;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class CreateSessionParam {

    @NotNull
    private String contractSlug;

    private Long duration;

    public Session toEntity() {
        Session session = new Session();
        session.setDuration(Objects.isNull(this.duration) ? 1 : this.duration);

        return session;
    }
}
