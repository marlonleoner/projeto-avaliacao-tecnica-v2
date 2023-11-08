package marlon.leoner.projeto.avaliacao.tecnica.service;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateAssociateParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectAlreadyExistsException;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.repository.AssociateRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AssociateService {

    private final static String ASSOCIATE_NOT_FOUND = "Não encontramos nenhum associado com o identificador informado.";
    private final static String ASSOCIATE_ALREADY_EXISTS = "O CPF informado já foi cadastrado anteriormente.";

    private final AssociateRepository repository;

    private final IntegrationService integrationService;

    public List<Associate> getAllAssociates() {
        return repository.findAll();
    }

    public Optional<Associate> getById(String id) {
        return repository.findById(id);
    }

    public Associate getAssociateOrExceptionById(String id) {
        Optional<Associate> associate = this.getById(id);
        return associate.orElseThrow(() -> new ObjectNotFoundException(ASSOCIATE_NOT_FOUND));
    }

    public void validateAssociateExists(String cpf) {
        Optional<Associate> associate = repository.findByCpf(cpf);
        associate.ifPresent((_associate) -> {
            throw new ObjectAlreadyExistsException(ASSOCIATE_ALREADY_EXISTS);
        });
    }

    public Associate createAssociate(CreateAssociateParam params) {
        Associate associate = params.toEntity();
        this.create(associate);

        return associate;
    }

    private void create(Associate associate) {
        associate.setAbleToVote(integrationService.isCpfValid(associate.getCpf()));

        this.save(associate);
    }

    private void save(Associate associate) {
        this.repository.save(associate);
    }


}
