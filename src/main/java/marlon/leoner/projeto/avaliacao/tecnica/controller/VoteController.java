package marlon.leoner.projeto.avaliacao.tecnica.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.VoteAggregation;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.VoteDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateVoteParam;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteAggregation aggregation;

    @ApiOperation(
            value = "Buscar todos os votos.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar todos os votos.", response = VoteDTO.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<List<VoteDTO>> getAllVotes() {
        return ResponseEntity.ok(aggregation.getAllVotes());
    }

    @ApiOperation(
            value = "Buscar um voto utilizando um identificador.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar o voto desejado.", response = VoteDTO.class),
            @ApiResponse(code = 404, message = "O voto não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VoteDTO> getVote(@PathVariable("id") String voteId) {
        return ResponseEntity.ok(aggregation.getVote(voteId));
    }

    @ApiOperation(
            value = "Cadastrar um novo voto.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao cadastrar um novo voto.")
    })
    @PostMapping
    public ResponseEntity<Void> createVote(@Valid @RequestBody CreateVoteParam params) {
        String voteId = aggregation.createVote(params);
        return ResponseEntity.created(URI.create("/vote/" + voteId)).build();
    }
}
