package marlon.leoner.projeto.avaliacao.tecnica.domain.params;

import lombok.Data;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Contract;

import javax.validation.constraints.NotNull;
import java.text.Normalizer;

@Data
public class CreateContractParam {

    @NotNull(message = "O campo 'name' é obrigatório.")
    private String name;

    @NotNull(message = "O campo 'description' é obrigatório.")
    private String description;

    public String getSlug() {
        String normalizedName = Normalizer.normalize(this.name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return normalizedName.toLowerCase().replace(" ", "-");
    }

    public Contract toEntity() {
        Contract contract = new Contract();
        contract.setName(this.getName());
        contract.setDescription(this.getDescription());
        contract.setSlug(this.getSlug());

        return contract;
    }
}
