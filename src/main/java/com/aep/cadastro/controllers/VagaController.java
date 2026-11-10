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

import com.aep.cadastro.models.Vaga;
import com.aep.cadastro.services.VagaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @GetMapping
    public ResponseEntity<List<Vaga>> listar() {
        return ResponseEntity.ok(vagaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable("id") String id) {
        return ResponseEntity.ok(vagaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Vaga> criar(@RequestBody Vaga vaga) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vagaService.criar(vaga));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizar(@PathVariable("id") String id, @RequestBody Vaga vaga) {
        return ResponseEntity.ok(vagaService.atualizar(id, vaga));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") String id) {
        vagaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
