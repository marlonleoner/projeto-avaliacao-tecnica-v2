package marlon.leoner.projeto.avaliacao.tecnica.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.SessionAggregation;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ResultSessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.SessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionAggregation aggregation;

    @ApiOperation(
            value = "Buscar todos as sessões.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar todos as sessões.", response = SessionDTO.class, responseContainer = "List")
    })
    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok(aggregation.getAllSessions());
    }

    @ApiOperation(
            value = "Buscar um associado utilizando um identificador.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar o associado desejado.", response = SessionDTO.class),
            @ApiResponse(code = 404, message = "O associado não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable("id") String sessionId) {
        return ResponseEntity.ok(aggregation.getSession(sessionId));
    }

    @ApiOperation(
            value = "Cadastrar uma nova sessão.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao cadastrar uma nova sessão.")
    })
    @PostMapping
    public ResponseEntity<Void> createSession(@Valid @RequestBody CreateSessionParam params) {
        String sessionId = aggregation.createSession(params);
        return ResponseEntity.created(URI.create("/session/" + sessionId)).build();
    }

    @ApiOperation(
            value = "Buscar o resultado de uma sessão.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso ao buscar o resultado da sessão.", response = ResultSessionDTO.class),
            @ApiResponse(code = 400, message = "O resultado não está disponível")
    })
    @GetMapping("/{id}/result")
    public ResponseEntity<ResultSessionDTO> getResultSession(@PathVariable("id") String sessionId) {
        return ResponseEntity.ok(aggregation.getResultSession(sessionId));
    }
}
