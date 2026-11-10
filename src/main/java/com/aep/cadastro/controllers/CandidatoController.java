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

import com.aep.cadastro.models.Candidato;
import com.aep.cadastro.services.CandidatoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping
    public ResponseEntity<List<Candidato>> listar() {
        return ResponseEntity.ok(candidatoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> buscarPorId(@PathVariable("id") String id) {
        return ResponseEntity.ok(candidatoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Candidato> criar(@RequestBody Candidato candidato) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatoService.criar(candidato));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizar(@PathVariable("id") String id, @RequestBody Candidato candidato) {
        return ResponseEntity.ok(candidatoService.atualizar(id, candidato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") String id) {
        candidatoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
