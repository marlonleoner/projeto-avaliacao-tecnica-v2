package marlon.leoner.projeto.avaliacao.tecnica.controller;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.ContractAggregation;

import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ContractDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateContractParam;
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

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        return ResponseEntity.ok(aggregation.getAllContracts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable String id) {
        return ResponseEntity.ok(aggregation.getContract(id));
    }

    @PostMapping
    public ResponseEntity<Void> createContract(@Valid @RequestBody CreateContractParam params) {
        String id = aggregation.createContract(params);
        return ResponseEntity.created(URI.create("/contract/" + id)).build();
    }
}
