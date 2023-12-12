package marlon.leoner.projeto.avaliacao.tecnica.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.ContractAggregation;

import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ContractDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/contract")
public class ContractController {

    private final ContractAggregation aggregation;

    @ApiOperation(
            value = "Buscar todos as pautas.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar todos as pautas.", response = ContractDTO.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        return ResponseEntity.ok(aggregation.getAllContracts());
    }

    @ApiOperation(
            value = "Buscar uma pauta utilizando um identificador.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar a pauta desejada.", response = ContractDTO.class),
            @ApiResponse(code = 404, message = "A pauta não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable String id) {
        return ResponseEntity.ok(aggregation.getContract(id));
    }

    @ApiOperation(
            value = "Cadastrar uma nova pauta.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao cadastrar uma nova pauta.")
    })
    @PostMapping
    public ResponseEntity<Void> createContract(@Valid @RequestBody CreateContractParam params) {
        String id = aggregation.createContract(params);
        return ResponseEntity.created(URI.create("/contract/" + id)).build();
    }
}
