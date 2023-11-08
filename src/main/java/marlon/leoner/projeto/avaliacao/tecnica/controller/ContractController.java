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
    private ResponseEntity<List<ContractDTO>> getAllContracts() {
        return ResponseEntity.ok(aggregation.getAllContracts());
    }

    @GetMapping("/{slug}")
    private ResponseEntity<ContractDTO> getContract(@PathVariable String slug) {
        return ResponseEntity.ok(aggregation.getContract(slug));
    }

    @PostMapping
    public ResponseEntity<Void> createContract(@Valid @RequestBody CreateContractParam params) {
        String slug = aggregation.createContract(params);
        return ResponseEntity.created(URI.create("/contract/" + slug)).build();
    }
}
