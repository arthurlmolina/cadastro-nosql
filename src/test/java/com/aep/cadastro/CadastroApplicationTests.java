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

	@Test
	void tratarOpcaoNaoNumerica() {
		String entrada =
			"abc\n " + "0\n";
		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
			CadastroApplication.main(new String[]{});
	}

	@Test
	void deveTratarIdadeNaoNumerica() {
		String entrada =
				"1\n" + "1\n" + "João\n" + "abc\n" + "20\n" + "Tenho interesse na vaga\n" + "0\n";

		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
		CadastroApplication.main(new String[]{});
	}

	@Test
	void gerenciarCurriculosEVoltar() {
		String entrada = "1\n" + "1\n" + "João\n" + "20\n" + "Obs Teste\n" + "2\n" + "0\n" + "0\n";

		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
			CadastroApplication.main(new String[]{});
	}

	@Test
	void editarCurriculo() {
		String entrada = "1\n" + "1\n" + "João\n" + "20\n" + "Observacao inicial\n" +
            "2\n" + "1\n" + "1\n" + "João Silva\n" + "21\n" + "Observacao atualizada\n" + "0\n";
		System.setIn(
			new ByteArrayInputStream(entrada.getBytes()));
			CadastroApplication.main(new String[]{});
	}

	@Test
	void deletarCurriculo() {
		String entrada = "1\n" +"1\n" + "Maria\n" + "25\n" + "Curriculo para exclusao\n" +
			"2\n" + "2\n" + "1\n" + "S\n" + "0\n";
		System.setIn(
			new ByteArrayInputStream(entrada.getBytes())
		);
		CadastroApplication.main(new String[]{});
	}

}
