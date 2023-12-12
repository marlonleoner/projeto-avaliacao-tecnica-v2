package marlon.leoner.projeto.avaliacao.tecnica.aggregation;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Associate;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.AssociateDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateAssociateParam;
import marlon.leoner.projeto.avaliacao.tecnica.exception.ObjectNotFoundException;
import marlon.leoner.projeto.avaliacao.tecnica.service.AssociateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AssociateAggregationTest {

    private AssociateAggregation aggregation;

    private AssociateService service;

    private Associate associateMock;

    private List<Associate> associatesMock;

    private Iterable<AssociateDTO> associatesMockDTOS;

    private CreateAssociateParam param;

    @BeforeEach
    void initService() {
        service = mock(AssociateService.class);
        aggregation = new AssociateAggregation(service);

        associateMock = generateAssociate("1");

        associatesMock = new ArrayList<>();
        associatesMock.add(generateAssociate("1"));
        associatesMock.add(generateAssociate("2"));
        associatesMock.add(generateAssociate("3"));

        associatesMockDTOS = generateAssociateDTOS();

        param = new CreateAssociateParam();
        param.setName("Associate 1");
        param.setCpf("19988877766");
    }

    private Associate generateAssociate(String id) {
        Associate associate = new Associate();
        associate.setId(id);
        associate.setName("Associate " + id);
        associate.setCpf(id + "9988877766");
        associate.setAbleToVote(Boolean.TRUE);
        associate.setCreatedAt(new Date());

        return associate;
    }

    private List<AssociateDTO> generateAssociateDTOS() {
        return associatesMock.stream()
                .map(Associate::toDto)
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar todos associados.")
    void validarSucessoAoBuscarAssociados() {
        when(service.getAllAssociates()).thenReturn(associatesMock);

        Iterable<AssociateDTO> associateDTOs = aggregation.getAllAssociates();
        assertIterableEquals(associatesMockDTOS, associateDTOs);
    }

    @Test
    @DisplayName("Validar SUCESSO ao buscar um associado.")
    void validarSucessoAoBuscarAssociado() {
        when(service.getAssociateOrExceptionById(anyString())).thenReturn(associateMock);

        AssociateDTO associateDTO = aggregation.getAssociateById(anyString());
        assertEquals(associateMock.toDto(), associateDTO);
    }

    @Test
    @DisplayName("Validar ERRO ao buscar um associado.")
    void validarErroAoBuscarAssociado() {
        when(service.getAssociateOrExceptionById(anyString())).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> aggregation.getAssociateById(anyString()));
    }

    @Test
    @DisplayName("Validar SUCESSO ao criar um associado.")
    void validarSucessoAoCriarAssociado() {
        when(service.createAssociate(any())).thenReturn(associateMock);

        String id = aggregation.createAssociate(param);

        assertEquals(associateMock.getId(), id);
    }
}
