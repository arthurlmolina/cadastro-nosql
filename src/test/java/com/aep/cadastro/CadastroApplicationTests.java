package com.aep.cadastro;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class CadastroApplicationTests {

    @Test
    void deveEncerrarSistemaAoDigitarZero() {

        String entrada = "0\n";

        System.setIn(
            new ByteArrayInputStream(entrada.getBytes()));
        CadastroApplication.main(new String[]{});
    }

	@Test
	void deveVerVagasEVoltar() {

			String entrada =
				"1\n" + " 0\n" + "0\n";

		System.setIn(new ByteArrayInputStream(entrada.getBytes()));
		CadastroApplication.main(new String[]{});
	}

	@Test
	void deveInformarVagaInvalida() {

		String entrada = "1\n" + "5\n" + "0\n";
		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
		CadastroApplication.main(new String[]{});
	}

	@Test
	void deveRealizarCandidatura() {

		String entrada =
				"1\n" + "1\n" + "João\n" +  "20\n" + "Tenho interesse\n" + "0\n";
		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
		CadastroApplication.main(new String[]{});
	}

}
