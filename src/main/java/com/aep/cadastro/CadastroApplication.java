package com.aep.cadastro;

import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class CadastroApplication {

	// Vagas fixas disponíveis
	private record Vaga(String titulo, String descricao, String salario) {
	}

	private static final Vaga[] VAGAS = {
			new Vaga("Desenvolvedor(a)",
					"Desenvolvimento de sistemas back-end e front-end, participação em code review e squads ágeis.",
					"R$ 4.850,00"),
			new Vaga("Auxiliar Administrativo",
					"Rotinas administrativas, organização de documentos, atendimento e suporte a equipes internas.",
					"R$ 2.200,00"),
			new Vaga("Auxiliar de Cozinha",
					"Apoio no preparo de alimentos, organização da cozinha e cumprimento das normas de higiene.",
					"R$ 2.500,00")
	};

	private static Scanner scanner;
	private static UserService userService;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CadastroApplication.class, args);

		userService = context.getBean(UserService.class);
		scanner = new Scanner(System.in);

		boolean continuar = true;
		while (continuar) {
			exibirMenuPrincipal();
			int opcao = lerOpcaoInt();

			switch (opcao) {
				case 1 -> verVagasECandidatar();
				case 2 -> gerenciarCurriculos();
				case 0 -> {
					System.out.println("\nEncerrando o sistema...");
					continuar = false;
				}
				default -> System.out.println("\nOpção inválida! Tente novamente.");
			}
		}
		scanner.close();
		context.close();
	}
	// menu
	private static void exibirMenuPrincipal() {
		System.out.println("\n===============================");
		System.out.println("       SISTEMA DE VAGAS");
		System.out.println("===============================");
		System.out.println("1 - Ver vagas disponíveis e se candidatar");
		System.out.println("2 - Ver meus currículos enviados");
		System.out.println("0 - Sair");
		System.out.print("Escolha uma opção: ");
	}

	// vagas
	private static void verVagasECandidatar() {
		System.out.println("\n=== VAGAS DISPONÍVEIS ===");
		for (int i = 0; i < VAGAS.length; i++) {
			Vaga vaga = VAGAS[i];
			System.out.println((i + 1) + " - " + vaga.titulo());
			System.out.println("    Descrição: " + vaga.descricao());
			System.out.println("    Salário: " + vaga.salario());
			System.out.println();
		}
		System.out.println("0 - Voltar");
		System.out.print("Escolha a vaga que deseja se candidatar: ");

		int escolha = lerOpcaoInt();
		if (escolha == 0) {
			return;
		}
		if (escolha < 1 || escolha > VAGAS.length) {
			System.out.println("\nVaga inválida!");
			return;
		}

		Vaga vagaEscolhida = VAGAS[escolha - 1];

		System.out.println("\n=== CANDIDATURA - " + vagaEscolhida.titulo() + " ===");

		System.out.print("Digite seu nome: ");
		String nome = scanner.nextLine();

		System.out.print("Digite sua idade: ");
		Integer idade = lerIdade();

		System.out.print("Deixe uma observação sobre você/seu interesse na vaga: ");
		String observacao = scanner.nextLine();

		UserModel user = new UserModel();
		user.setNome(nome);
		user.setIdade(idade);
		user.setVaga(vagaEscolhida.titulo());
		user.setObservacao(observacao);

		userService.createUser(user);

		System.out.println("\n=== CANDIDATURA ENVIADA COM SUCESSO ===");
		System.out.println("Nome: " + user.getNome());
		System.out.println("Idade: " + user.getIdade());
		System.out.println("Vaga: " + user.getVaga());
		System.out.println("Observação: " + user.getObservacao());
	}

	// curriculos
	private static void gerenciarCurriculos() {
		List<UserModel> curriculos = userService.getAllUsers();

		if (curriculos.isEmpty()) {
			System.out.println("\nNenhum currículo enviado ainda.");
			return;
		}

		System.out.println("\n=== MEUS CURRÍCULOS ENVIADOS ===");
		for (int i = 0; i < curriculos.size(); i++) {
			UserModel u = curriculos.get(i);
			System.out.println((i + 1) + " - " + u.getNome() + " | Idade: " + u.getIdade()
					+ " | Vaga: " + u.getVaga());
			System.out.println("    Observação: " + u.getObservacao());
			System.out.println("    ID: " + u.getId());
			System.out.println();
		}

		System.out.println("1 - Editar Currículo");
		System.out.println("2 - Deletar Currículo");
		System.out.println("0 - Voltar");
		System.out.print("Escolha uma opção: ");

		int opcao = lerOpcaoInt();

		switch (opcao) {
			case 1 -> editarCurriculo(curriculos);
			case 2 -> deletarCurriculo(curriculos);
			case 0 -> {
			}
			default -> System.out.println("\nOpção inválida!");
		}
	}

	private static void editarCurriculo(List<UserModel> curriculos) {
		System.out.print("\nDigite o número do currículo que deseja editar: ");
		int indice = lerOpcaoInt() - 1;

		if (indice < 0 || indice >= curriculos.size()) {
			System.out.println("\nCurrículo inválido!");
			return;
		}

		UserModel selecionado = curriculos.get(indice);

		System.out.println("\nDeixe em branco (Enter) para manter o valor atual.");

		System.out.print("Nome atual [" + selecionado.getNome() + "]: ");
		String nome = scanner.nextLine();
		if (!nome.isBlank()) {
			selecionado.setNome(nome);
		}

		System.out.print("Idade atual [" + selecionado.getIdade() + "]: ");
		String idadeStr = scanner.nextLine();
		if (!idadeStr.isBlank()) {
			try {
				selecionado.setIdade(Integer.parseInt(idadeStr));
			} catch (NumberFormatException e) {
				System.out.println("Idade inválida, mantendo valor anterior.");
			}
		}

		System.out.print("Observação atual [" + selecionado.getObservacao() + "]: ");
		String observacao = scanner.nextLine();
		if (!observacao.isBlank()) {
			selecionado.setObservacao(observacao);
		}

		userService.updateUser(selecionado, selecionado.getId());

		System.out.println("\n=== CURRÍCULO ATUALIZADO COM SUCESSO ===");
	}

	private static void deletarCurriculo(List<UserModel> curriculos) {
		System.out.print("\nDigite o número do currículo que deseja deletar: ");
		int indice = lerOpcaoInt() - 1;

		if (indice < 0 || indice >= curriculos.size()) {
			System.out.println("\nCurrículo inválido!");
			return;
		}

		UserModel selecionado = curriculos.get(indice);

		System.out.print("Tem certeza que deseja deletar o currículo de \""
				+ selecionado.getNome() + "\"? (S/N): ");
		String confirmacao = scanner.nextLine();

		if (confirmacao.equalsIgnoreCase("S")) {
			userService.deleteUser(selecionado.getId());
			System.out.println("\n=== CURRÍCULO DELETADO COM SUCESSO ===");
		} else {
			System.out.println("\nOperação cancelada.");
		}
	}

	// leitura
	private static int lerOpcaoInt() {
		while (!scanner.hasNextInt()) {
			System.out.print("Entrada inválida, digite um número: ");
			scanner.next();
		}
		int valor = scanner.nextInt();
		scanner.nextLine();
		return valor;
	}2

	private static Integer lerIdade() {
		while (!scanner.hasNextInt()) {
			System.out.print("Idade inválida, digite um número: ");
			scanner.next();
		}
		int idade = scanner.nextInt();
		scanner.nextLine();
		return idade;
	}
}