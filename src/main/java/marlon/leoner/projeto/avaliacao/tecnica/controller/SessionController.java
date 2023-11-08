package marlon.leoner.projeto.avaliacao.tecnica.controller;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.SessionAggregation;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.ResultSessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.dto.SessionDTO;
import marlon.leoner.projeto.avaliacao.tecnica.domain.params.CreateSessionParam;

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

    @GetMapping
    private ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok(aggregation.getAllSessions());
    }

    @GetMapping("/{id}")
    private ResponseEntity<SessionDTO> getSession(@PathVariable("id") String sessionId) {
        return ResponseEntity.ok(aggregation.getSession(sessionId));
    }

    @PostMapping
    private ResponseEntity<Void> createSession(@Valid @RequestBody CreateSessionParam params) {
        String sessionId = aggregation.createSession(params);
        return ResponseEntity.created(URI.create("/session/" + sessionId)).build();
    }

    @GetMapping("/{id}/result")
    private ResponseEntity<ResultSessionDTO> getResultSession(@PathVariable("id") String sessionId) {
        return ResponseEntity.ok(aggregation.getResultSession(sessionId));
    }
}
