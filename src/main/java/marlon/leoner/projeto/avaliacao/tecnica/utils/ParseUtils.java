package marlon.leoner.projeto.avaliacao.tecnica.utils;

import marlon.leoner.projeto.avaliacao.tecnica.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;

public class ParseUtils {

    private static final String INVALID_CPF = "O CPF informado é inválido.";

    private ParseUtils() {
        // empty constructor
    }

    public static String maskCpf(String cpf) {
        if (StringUtils.isBlank(cpf) || cpf.length() != 11) {
            throw new InvalidParameterException(INVALID_CPF);
        }

        String init = cpf.substring(0, 3);
        String end = cpf.substring(9, 11);

        return init + "******" + end;
    }
}
