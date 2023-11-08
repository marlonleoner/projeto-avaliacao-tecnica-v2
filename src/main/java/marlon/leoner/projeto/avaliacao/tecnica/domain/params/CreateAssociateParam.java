package marlon.leoner.projeto.avaliacao.tecnica.domain.params;

import lombok.Data;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateAssociateParam {

    @NotNull(message = "O campo 'cpf' não pode ser nulo.")
    @Size(min = 11, max = 11, message = "O campo 'cpf' deve possuir 11 digítos.")
    private String cpf;

    @NotNull(message = "O campo 'name' não pode ser nulo.")
    private String name;

    public Associate toEntity() {
        Associate associate = new Associate();
        associate.setCpf(this.cpf);
        associate.setName(this.name);

        return associate;
    }
}
