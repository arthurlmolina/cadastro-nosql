package com.aep.cadastro;

import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class CadastroApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CadastroApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);

		Scanner scanner = new Scanner(System.in);

		System.out.println("\n\n\n\n=== CADASTRO DE USUÁRIO ===");

		System.out.print("Digite seu nome: ");
		String nome = scanner.nextLine();

		System.out.print("Digite sua idade: ");
		Integer idade = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Digite a vaga desejada: ");
		String vaga = scanner.nextLine();

		UserModel user = new UserModel();
		user.setNome(nome);
		user.setIdade(idade);
		user.setVaga(vaga);

		userRepository.save(user);

		System.out.println("\n=== USUÁRIO CADASTRADO COM SUCESSO ===");
		System.out.println("Nome: " + user.getNome());
		System.out.println("Idade: " + user.getIdade());
		System.out.println("Vaga: " + user.getVaga());
	}
}
