package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import lombok.AllArgsConstructor;
import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.AssociateDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateAssociateParam;
import marlon.leoner.projeto.avaliacao.tecnica.service.AssociateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AssociateAggregation {

    private final AssociateService service;

    public List<AssociateDTO> getAllAssociates() {
        List<Associate> associates = service.getAllAssociates();
        return associates.stream()
                .map(Associate::toDto)
                .collect(Collectors.toList());
    }

    public AssociateDTO getAssociateById(String id) {
        Associate associate = service.getAssociateOrExceptionById(id);

        return associate.toDto();
    }

    public String createAssociate(CreateAssociateParam params) {
        service.validateAssociateExists(params.getCpf());
        Associate associate = service.createAssociate(params);

        return associate.getId();
    }
}
