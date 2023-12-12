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

    @ApiOperation(
            value = "Buscar todos os associados.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar todos os associados.", response = AssociateDTO.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<List<AssociateDTO>> getAllAssociates() {
        return ResponseEntity.ok(aggregation.getAllAssociates());
    }

    @ApiOperation(
            value = "Buscar um associado utilizando um identificador.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar o associado desejado.", response = AssociateDTO.class),
            @ApiResponse(code = 404, message = "O associado não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssociateDTO> getAssociate(@PathVariable("id") String id) {
        return ResponseEntity.ok(aggregation.getAssociateById(id));
    }

    @ApiOperation(
            value = "Cadastrar um novo associado.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao cadastrar um novo associado.")
    })
    @PostMapping
    public ResponseEntity<Void> createAssociate(@Valid @RequestBody CreateAssociateParam params) {
        String id = aggregation.createAssociate(params);
        return ResponseEntity.created(URI.create("/associate/" + id)).build();
    }
}
