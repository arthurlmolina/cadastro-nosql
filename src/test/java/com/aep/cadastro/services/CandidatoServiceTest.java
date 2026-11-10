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
import com.aep.cadastro.models.Candidato;
import com.aep.cadastro.models.Endereco;
import com.aep.cadastro.repositories.CandidatoRepository;
import com.aep.cadastro.repositories.CandidaturaRepository;

@ExtendWith(MockitoExtension.class)
class CandidatoServiceTest {

    @Mock
    private CandidatoRepository repository;

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @InjectMocks
    private CandidatoService service;

    private Candidato candidatoValido() {
        Candidato c = new Candidato();
        c.setNome("João");
        c.setEmail("joao@email.com");
        c.setIdade(20);
        c.getEnderecos().add(new Endereco("Rua A", "Maringá", "PR"));
        return c;
    }

    @Test
    void deveCriarCandidatoComEndereco() {
        when(repository.save(any(Candidato.class))).thenAnswer(i -> i.getArgument(0));

        Candidato resultado = service.criar(candidatoValido());

        assertEquals("João", resultado.getNome());
        assertEquals(1, resultado.getEnderecos().size());
        assertEquals("Maringá", resultado.getEnderecos().get(0).getCidade());
    }

    @Test
    void deveCriarCandidatoQuandoListaDeEnderecosVemNula() {
        when(repository.save(any(Candidato.class))).thenAnswer(i -> i.getArgument(0));
        Candidato c = candidatoValido();
        c.setEnderecos(null);

        assertEquals(0, service.criar(c).getEnderecos().size());
    }

    @Test
    void naoDeveCriarSemNome() {
        Candidato c = candidatoValido();
        c.setNome("");
        assertThrows(RegraNegocioException.class, () -> service.criar(c));
        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveCriarComEmailInvalido() {
        Candidato c = candidatoValido();
        c.setEmail("emailsemarroba");
        assertThrows(RegraNegocioException.class, () -> service.criar(c));
    }

    @Test
    void naoDeveCriarComIdadeZero() {
        Candidato c = candidatoValido();
        c.setIdade(0);
        assertThrows(RegraNegocioException.class, () -> service.criar(c));
    }

    @Test
    void naoDeveCriarComEnderecoSemCidade() {
        Candidato c = candidatoValido();
        c.getEnderecos().add(new Endereco("Rua B", "", "PR"));
        assertThrows(RegraNegocioException.class, () -> service.criar(c));
    }

    @Test
    void deveListarCandidatos() {
        when(repository.findAll()).thenReturn(List.of(candidatoValido()));
        assertEquals(1, service.listar().size());
    }

    @Test
    void deveBuscarPorId() {
        when(repository.findById("1")).thenReturn(Optional.of(candidatoValido()));
        assertEquals("João", service.buscarPorId("1").getNome());
    }

    @Test
    void deveFalharAoBuscarIdInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId("x"));
    }

    @Test
    void deveAtualizarCandidato() {
        when(repository.findById("1")).thenReturn(Optional.of(candidatoValido()));
        when(repository.save(any(Candidato.class))).thenAnswer(i -> i.getArgument(0));
        Candidato dados = candidatoValido();
        dados.setNome("João Silva");
        dados.setIdade(21);

        Candidato resultado = service.atualizar("1", dados);

        assertEquals("João Silva", resultado.getNome());
        assertEquals(21, resultado.getIdade());
    }

    @Test
    void naoDeveAtualizarComDadosInvalidos() {
        when(repository.findById("1")).thenReturn(Optional.of(candidatoValido()));
        Candidato dados = candidatoValido();
        dados.setNome(" ");
        assertThrows(RegraNegocioException.class, () -> service.atualizar("1", dados));
    }

    @Test
    void naoDeveAtualizarInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar("x", candidatoValido()));
    }

    @Test
    void deveRemoverCandidatoEAsCandidaturasDele() {
        when(repository.findById("1")).thenReturn(Optional.of(candidatoValido()));
        service.remover("1");
        verify(candidaturaRepository).deleteByCandidatoId("1");
        verify(repository).deleteById("1");
    }

    @Test
    void naoDeveRemoverInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> service.remover("x"));
        verify(candidaturaRepository, never()).deleteByCandidatoId(any());
        verify(repository, never()).deleteById(any());
    }
}
