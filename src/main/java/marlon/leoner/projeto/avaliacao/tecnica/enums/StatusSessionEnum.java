package marlon.leoner.projeto.avaliacao.tecnica.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusSessionEnum {

    CREATED(1, "Sessão criada."),
    OPEN(2, "Sessão aberta"),
    CLOSED(3, "Sessão encerrada"),
    NOTIFIED(4, "Resultado nofiticado");

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
}
