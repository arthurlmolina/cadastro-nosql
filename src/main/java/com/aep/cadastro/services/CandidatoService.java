package com.aep.cadastro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aep.cadastro.exceptions.RecursoNaoEncontradoException;
import com.aep.cadastro.exceptions.RegraNegocioException;
import com.aep.cadastro.models.Candidato;
import com.aep.cadastro.models.Endereco;
import com.aep.cadastro.repositories.CandidatoRepository;
import com.aep.cadastro.repositories.CandidaturaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final CandidaturaRepository candidaturaRepository;

    public List<Candidato> listar() {
        return candidatoRepository.findAll();
    }

    public Candidato buscarPorId(String id) {
        return candidatoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado: " + id));
    }

    public Candidato criar(Candidato candidato) {
        validar(candidato);
        candidato.setId(null);
        return candidatoRepository.save(candidato);
    }

    public Candidato atualizar(String id, Candidato dados) {
        Candidato candidato = buscarPorId(id);
        validar(dados);
        candidato.setNome(dados.getNome());
        candidato.setEmail(dados.getEmail());
        candidato.setIdade(dados.getIdade());
        candidato.setEnderecos(dados.getEnderecos());
        return candidatoRepository.save(candidato);
    }

    /** Remove o candidato e as candidaturas dele, para não sobrarem registros apontando para um id inexistente. */
    public void remover(String id) {
        buscarPorId(id);
        candidaturaRepository.deleteByCandidatoId(id);
        candidatoRepository.deleteById(id);
    }

    private void validar(Candidato candidato) {
        if (candidato.getNome() == null || candidato.getNome().isBlank()) {
            throw new RegraNegocioException("Nome é obrigatório");
        }
        if (candidato.getEmail() == null || !candidato.getEmail().contains("@")) {
            throw new RegraNegocioException("E-mail inválido");
        }
        if (candidato.getIdade() == null || candidato.getIdade() <= 0) {
            throw new RegraNegocioException("Idade deve ser maior que zero");
        }
        if (candidato.getEnderecos() == null) {
            candidato.setEnderecos(new ArrayList<>());
        }
        for (Endereco endereco : candidato.getEnderecos()) {
            if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
                throw new RegraNegocioException("Cidade do endereço é obrigatória");
            }
        }
    }
}
