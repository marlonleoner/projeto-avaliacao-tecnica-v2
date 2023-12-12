package marlon.leoner.projeto.avaliacao.tecnica.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusSessionEnum {

    CREATED(1, "Sessão cadastrada"),
    OPEN(2, "Sessão aberta"),
    CLOSED(3, "Sessão encerrada"),
    NOTIFIED(4, "Resultado da sessão notificada aos associados");

    private final Integer id;

    private final String description;

    public static StatusSessionEnum getInstanceById(Integer id) {
        for (StatusSessionEnum item : values()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    public boolean isCreated() {
        return CREATED.equals(this);
    }

    public boolean isOpened() {
        return OPEN.equals(this);
    }

    public boolean isClosed() {
        return CLOSED.equals(this);
    }

    public boolean isNotified() {
        return NOTIFIED.equals(this);
    }
}
