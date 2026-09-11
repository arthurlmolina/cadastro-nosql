package com.aep.cadastro;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class CadastroApplicationTests {

    @Test
    void deveEncerrarSistemaAoDigitarZero() {

        String entrada = "0\n";

        System.setIn(
            new ByteArrayInputStream(entrada.getBytes())
        );

        CadastroApplication.main(new String[]{});
    }
}
