package marlon.leoner.projeto.avaliacao.tecnica.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.AssociateAggregation;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.AssociateDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateAssociateParam;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api
@AllArgsConstructor
@RestController
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateAggregation aggregation;

    @GetMapping
    @ApiOperation(value = "Carregar todos os associados", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar todos os associados.", response = AssociateDTO.class, responseContainer = "List")
    })
    public ResponseEntity<List<AssociateDTO>> getAllAssociates() {
        return ResponseEntity.ok(aggregation.getAllAssociates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociateDTO> getAssociate(@PathVariable("id") String id) {
        return ResponseEntity.ok(aggregation.getAssociateById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createAssociate(@Valid @RequestBody CreateAssociateParam params) {
        String id = aggregation.createAssociate(params);
        return ResponseEntity.created(URI.create("/associate/" + id)).build();
    }
}
