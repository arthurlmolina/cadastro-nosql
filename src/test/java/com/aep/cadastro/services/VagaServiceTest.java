package com.aep.cadastro.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aep.cadastro.exceptions.RecursoNaoEncontradoException;
import com.aep.cadastro.exceptions.RegraNegocioException;
import com.aep.cadastro.models.Vaga;
import com.aep.cadastro.repositories.CandidaturaRepository;
import com.aep.cadastro.repositories.VagaRepository;

@ExtendWith(MockitoExtension.class)
class VagaServiceTest {

    @Mock
    private VagaRepository repository;

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @InjectMocks
    private VagaService service;

    private Vaga vagaValida() {
        Vaga v = new Vaga();
        v.setTitulo("Desenvolvedor Java");
        v.setEmpresa("Tech Ltda");
        v.setDescricao("Desenvolvimento de APIs");
        return v;
    }

    @Test
    void deveCriarVaga() {
        when(repository.save(any(Vaga.class))).thenAnswer(i -> i.getArgument(0));
        assertEquals("Desenvolvedor Java", service.criar(vagaValida()).getTitulo());
    }

    @Test
    void naoDeveCriarSemTitulo() {
        Vaga v = vagaValida();
        v.setTitulo("");
        assertThrows(RegraNegocioException.class, () -> service.criar(v));
        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveCriarSemEmpresa() {
        Vaga v = vagaValida();
        v.setEmpresa(null);
        assertThrows(RegraNegocioException.class, () -> service.criar(v));
    }

    @Test
    void deveBuscarPorId() {
        when(repository.findById("1")).thenReturn(Optional.of(vagaValida()));
        assertEquals("Tech Ltda", service.buscarPorId("1").getEmpresa());
    }

    @Test
    void deveFalharAoBuscarIdInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId("x"));
    }

    @Test
    void naoDeveAtualizarComDadosInvalidos() {
        when(repository.findById("1")).thenReturn(Optional.of(vagaValida()));
        Vaga dados = vagaValida();
        dados.setTitulo(null);
        assertThrows(RegraNegocioException.class, () -> service.atualizar("1", dados));
    }

    @Test
    void deveAtualizarVaga() {
        when(repository.findById("1")).thenReturn(Optional.of(vagaValida()));
        when(repository.save(any(Vaga.class))).thenAnswer(i -> i.getArgument(0));
        Vaga dados = vagaValida();
        dados.setTitulo("Analista");

        assertEquals("Analista", service.atualizar("1", dados).getTitulo());
    }

    @Test
    void deveRemoverVagaEAsCandidaturasDela() {
        when(repository.findById("1")).thenReturn(Optional.of(vagaValida()));
        service.remover("1");
        verify(candidaturaRepository).deleteByVagaId("1");
        verify(repository).deleteById("1");
    }

    @Test
    void naoDeveRemoverInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.remover("x"));
        verify(candidaturaRepository, never()).deleteByVagaId(any());
        verify(repository, never()).deleteById(any());
    }
}
