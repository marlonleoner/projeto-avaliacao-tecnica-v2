package marlon.leoner.projeto.avaliacao.tecnica.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractException extends RuntimeException {

    private String id;

    public AbstractException(String message) {
        super(message);
        this.id = this.getClass().getSimpleName().replace("Exception", "Error");
    }
}