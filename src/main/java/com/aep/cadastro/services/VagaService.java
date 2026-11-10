package com.aep.cadastro.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aep.cadastro.exceptions.RecursoNaoEncontradoException;
import com.aep.cadastro.exceptions.RegraNegocioException;
import com.aep.cadastro.models.Vaga;
import com.aep.cadastro.repositories.CandidaturaRepository;
import com.aep.cadastro.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final CandidaturaRepository candidaturaRepository;

    public List<Vaga> listar() {
        return vagaRepository.findAll();
    }

    public Vaga buscarPorId(String id) {
        return vagaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada: " + id));
    }

    public Vaga criar(Vaga vaga) {
        validar(vaga);
        vaga.setId(null);
        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(String id, Vaga dados) {
        Vaga vaga = buscarPorId(id);
        validar(dados);
        vaga.setTitulo(dados.getTitulo());
        vaga.setEmpresa(dados.getEmpresa());
        vaga.setDescricao(dados.getDescricao());
        return vagaRepository.save(vaga);
    }

    /** Remove a vaga e as candidaturas feitas para ela, para não sobrarem registros apontando para um id inexistente. */
    public void remover(String id) {
        buscarPorId(id);
        candidaturaRepository.deleteByVagaId(id);
        vagaRepository.deleteById(id);
    }

    private void validar(Vaga vaga) {
        if (vaga.getTitulo() == null || vaga.getTitulo().isBlank()) {
            throw new RegraNegocioException("Título é obrigatório");
        }
        if (vaga.getEmpresa() == null || vaga.getEmpresa().isBlank()) {
            throw new RegraNegocioException("Empresa é obrigatória");
        }
    }
}
