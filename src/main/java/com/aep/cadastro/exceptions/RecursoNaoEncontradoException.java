package com.aep.cadastro.exceptions;

/** Registro não existe no banco (retorna 404). */
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
