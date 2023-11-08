package marlon.leoner.projeto.avaliacao.tecnica.enums;

import lombok.Getter;

@Getter
public enum VoteTypeEnum {

    YES("S", "Sim"),
    NO("N", "Não");

    private final String value;
    private final String description;

    VoteTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static VoteTypeEnum getInstance(String vote) {
        for (VoteTypeEnum value : values()) {
            if (value.getValue().equals(vote)) {
                return value;
            }
        }

        return null;
    }
}
