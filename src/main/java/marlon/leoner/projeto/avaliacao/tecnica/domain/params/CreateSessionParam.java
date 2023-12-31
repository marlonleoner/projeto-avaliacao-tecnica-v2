package marlon.leoner.projeto.avaliacao.tecnica.domain.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
public class CreateSessionParam {

    @NotNull(message = "Uma pauta deve ser informada.")
    private String contractId;

    @NotNull(message = "Uma data para inicio da sessão deve ser informada.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "Mensagem do FutureOrPresent")
    private Date startDate;

    private Long duration;

    public Session toEntity() {
        Session session = new Session();
        session.setDuration(Objects.isNull(this.duration) ? 1 : this.duration);
        session.setOpenedAt(Objects.isNull(this.startDate) ? new Date() : this.startDate);

        return session;
    }
}
