package marlon.leoner.projeto.avaliacao.tecnica.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Error {

    private String code;

    private List<String> message;
}