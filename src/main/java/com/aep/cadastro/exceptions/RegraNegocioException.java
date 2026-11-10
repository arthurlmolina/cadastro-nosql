package com.aep.cadastro.exceptions;

/** Dados inválidos enviados pelo usuário (retorna 400). */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
