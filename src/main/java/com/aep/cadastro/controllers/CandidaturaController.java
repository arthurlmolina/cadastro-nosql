package com.aep.cadastro.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aep.cadastro.models.Candidatura;
import com.aep.cadastro.services.CandidaturaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidaturas")
@RequiredArgsConstructor
public class CandidaturaController {

    private final CandidaturaService candidaturaService;

    @GetMapping
    public ResponseEntity<List<Candidatura>> listar() {
        return ResponseEntity.ok(candidaturaService.listar());
    }

    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<List<Candidatura>> listarPorCandidato(@PathVariable("candidatoId") String candidatoId) {
        return ResponseEntity.ok(candidaturaService.listarPorCandidato(candidatoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidatura> buscarPorId(@PathVariable("id") String id) {
        return ResponseEntity.ok(candidaturaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Candidatura> criar(@RequestBody Candidatura candidatura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidaturaService.criar(candidatura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidatura> atualizar(@PathVariable("id") String id,
            @RequestBody Candidatura candidatura) {
        return ResponseEntity.ok(candidaturaService.atualizar(id, candidatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") String id) {
        candidaturaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
