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
import com.aep.cadastro.models.Candidatura;
import com.aep.cadastro.repositories.CandidaturaRepository;
import com.aep.cadastro.repositories.CandidatoRepository;
import com.aep.cadastro.repositories.VagaRepository;

@ExtendWith(MockitoExtension.class)
class CandidaturaServiceTest {

    @Mock
    private CandidaturaRepository candidaturaRepository;
    @Mock
    private CandidatoRepository candidatoRepository;
    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private CandidaturaService service;

    private Candidatura candidaturaValida() {
        Candidatura c = new Candidatura();
        c.setCandidatoId("c1");
        c.setVagaId("v1");
        c.setObservacao("Tenho interesse");
        return c;
    }

    @Test
    void deveCriarCandidaturaQuandoCandidatoEVagaExistem() {
        when(candidatoRepository.existsById("c1")).thenReturn(true);
        when(vagaRepository.existsById("v1")).thenReturn(true);
        when(candidaturaRepository.save(any(Candidatura.class))).thenAnswer(i -> i.getArgument(0));

        Candidatura resultado = service.criar(candidaturaValida());

        assertEquals("c1", resultado.getCandidatoId());
        assertEquals("v1", resultado.getVagaId());
    }

    @Test
    void naoDeveCriarSemObservacao() {
        Candidatura c = candidaturaValida();
        c.setObservacao("");
        assertThrows(RegraNegocioException.class, () -> service.criar(c));
        verify(candidaturaRepository, never()).save(any());
    }

    @Test
    void naoDeveCriarComCandidatoInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.criar(candidaturaValida()));
        verify(candidaturaRepository, never()).save(any());
    }

    @Test
    void naoDeveCriarSemInformarCandidato() {
        Candidatura c = candidaturaValida();
        c.setCandidatoId(null);
        assertThrows(RecursoNaoEncontradoException.class, () -> service.criar(c));
    }

    @Test
    void naoDeveCriarComVagaInexistente() {
        when(candidatoRepository.existsById("c1")).thenReturn(true);
        assertThrows(RecursoNaoEncontradoException.class, () -> service.criar(candidaturaValida()));
    }

    @Test
    void naoDeveCriarSemInformarVaga() {
        when(candidatoRepository.existsById("c1")).thenReturn(true);
        Candidatura c = candidaturaValida();
        c.setVagaId(null);
        assertThrows(RecursoNaoEncontradoException.class, () -> service.criar(c));
    }

    @Test
    void deveListarTodas() {
        when(candidaturaRepository.findAll()).thenReturn(List.of(candidaturaValida()));
        assertEquals(1, service.listar().size());
    }

    @Test
    void deveListarPorCandidato() {
        when(candidaturaRepository.findByCandidatoId("c1")).thenReturn(List.of(candidaturaValida()));
        assertEquals(1, service.listarPorCandidato("c1").size());
    }

    @Test
    void deveBuscarPorId() {
        when(candidaturaRepository.findById("1")).thenReturn(Optional.of(candidaturaValida()));
        assertEquals("c1", service.buscarPorId("1").getCandidatoId());
    }

    @Test
    void deveFalharAoBuscarIdInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId("x"));
    }

    @Test
    void deveAtualizarObservacao() {
        when(candidaturaRepository.findById("1")).thenReturn(Optional.of(candidaturaValida()));
        when(candidaturaRepository.save(any(Candidatura.class))).thenAnswer(i -> i.getArgument(0));
        Candidatura dados = candidaturaValida();
        dados.setObservacao("Nova observação");

        assertEquals("Nova observação", service.atualizar("1", dados).getObservacao());
    }

    @Test
    void naoDeveAtualizarSemObservacao() {
        when(candidaturaRepository.findById("1")).thenReturn(Optional.of(candidaturaValida()));
        Candidatura dados = candidaturaValida();
        dados.setObservacao(null);
        assertThrows(RegraNegocioException.class, () -> service.atualizar("1", dados));
    }

    @Test
    void deveRemoverCandidatura() {
        when(candidaturaRepository.findById("1")).thenReturn(Optional.of(candidaturaValida()));
        service.remover("1");
        verify(candidaturaRepository).deleteById("1");
    }

    @Test
    void naoDeveRemoverInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.remover("x"));
    }
}
