package com.aep.cadastro.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aep.cadastro.exceptions.RecursoNaoEncontradoException;
import com.aep.cadastro.exceptions.RegraNegocioException;
import com.aep.cadastro.models.Candidatura;
import com.aep.cadastro.repositories.CandidaturaRepository;
import com.aep.cadastro.repositories.CandidatoRepository;
import com.aep.cadastro.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;

    public List<Candidatura> listar() {
        return candidaturaRepository.findAll();
    }

    public List<Candidatura> listarPorCandidato(String candidatoId) {
        return candidaturaRepository.findByCandidatoId(candidatoId);
    }

    public Candidatura buscarPorId(String id) {
        return candidaturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Candidatura não encontrada: " + id));
    }

    /** O relacionamento é garantido aqui: o candidato e a vaga precisam existir. */
    public Candidatura criar(Candidatura candidatura) {
        validarObservacao(candidatura);
        if (candidatura.getCandidatoId() == null || !candidatoRepository.existsById(candidatura.getCandidatoId())) {
            throw new RecursoNaoEncontradoException("Candidato não encontrado: " + candidatura.getCandidatoId());
        }
        if (candidatura.getVagaId() == null || !vagaRepository.existsById(candidatura.getVagaId())) {
            throw new RecursoNaoEncontradoException("Vaga não encontrada: " + candidatura.getVagaId());
        }
        candidatura.setId(null);
        return candidaturaRepository.save(candidatura);
    }

    public Candidatura atualizar(String id, Candidatura dados) {
        Candidatura candidatura = buscarPorId(id);
        validarObservacao(dados);
        candidatura.setObservacao(dados.getObservacao());
        return candidaturaRepository.save(candidatura);
    }

    public void remover(String id) {
        buscarPorId(id);
        candidaturaRepository.deleteById(id);
    }

    private void validarObservacao(Candidatura candidatura) {
        if (candidatura.getObservacao() == null || candidatura.getObservacao().isBlank()) {
            throw new RegraNegocioException("Observação é obrigatória");
        }
    }
}
