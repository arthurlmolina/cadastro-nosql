package com.aep.cadastro.services;

import com.aep.cadastro.CadastroApplication;
import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CadastroApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void limparBanco() {
        userRepository.deleteAll();
    }


    // Teste de funcionamento do CRUD
    @Test
    void deveCriarUsuario() {
        UserModel user = new UserModel();
        user.setNome("João");
        user.setIdade(20);
        user.setVaga("Desenvolvedor");
        user.setObservacao("Candidato disponível");

        UserModel resultado = userService.createUser(user);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("João", resultado.getNome());
        assertEquals(20, resultado.getIdade());
    }

    @Test
    void deveBuscarTodosUsuarios() {
        UserModel user = new UserModel();
        user.setNome("Maria");
        user.setIdade(22);
        user.setVaga("Desenvolvedora");
        user.setVaga("Desenvolvedor");
        user.setObservacao("Candidato disponível");

        userService.createUser(user);

        List<UserModel> usuarios = userService.getAllUsers();

        assertEquals(1, usuarios.size());
        assertEquals("Maria", usuarios.get(0).getNome());
    }

    @Test
    void deveBuscarUsuarioPorId() {
    UserModel user = new UserModel();
    user.setNome("Carlos");
    user.setIdade(25);
    user.setVaga("Analista");
    user.setVaga("Desenvolvedor");
        user.setObservacao("Candidato disponível");

    UserModel salvo = userService.createUser(user);

    Optional<UserModel> resultado = userService.findById(salvo.getId());

    assertTrue(resultado.isPresent());
    assertEquals("Carlos", resultado.get().getNome());
    assertEquals(25, resultado.get().getIdade());
}

    @Test
    void deveAtualizarUsuario() {
    UserModel user = new UserModel();
    user.setNome("Ana");
    user.setIdade(22);
    user.setVaga("Estagiária");
    user.setObservacao("Candidato disponível");

    UserModel salvo = userService.createUser(user);

    UserModel dadosAtualizados = new UserModel();
    dadosAtualizados.setNome("Ana Souza");
    dadosAtualizados.setIdade(23);
    dadosAtualizados.setVaga("Desenvolvedora");
    dadosAtualizados.setObservacao("Promovida");

    UserModel resultado =
            userService.updateUser(dadosAtualizados, salvo.getId());

    assertEquals("Ana Souza", resultado.getNome());
    assertEquals(23, resultado.getIdade());
    assertEquals("Desenvolvedora", resultado.getVaga());
    assertEquals("Promovida", resultado.getObservacao());
    }

    @Test
    void deveDeletarUsuario() {
    UserModel user = new UserModel();
    user.setNome("Pedro");
    user.setIdade(30);
    user.setVaga("Desenvolvedor");
    user.setObservacao("Candidato disponível");

    UserModel salvo = userService.createUser(user);

    userService.deleteUser(salvo.getId());

    Optional<UserModel> resultado = userService.findById(salvo.getId());

    assertTrue(resultado.isEmpty());
    }

    // testes de criação de usuario
    @Test
    void naoDeveCriarUsuarioSemNome() {
    UserModel user = new UserModel();
    user.setNome("");
    user.setIdade(20);
    user.setVaga("Desenvolvedor");
    user.setObservacao("Candidato disponível");

    assertThrows(
        IllegalArgumentException.class,
        () -> userService.createUser(user));

    }

    @Test
    void naoDeveCriarUsuarioComIdadeZero( ) {
    UserModel user = new UserModel();
    user.setNome("João");
    user.setIdade(0);
    user.setVaga("Desenvolvedor");
    user.setObservacao("Candidato disponível");

    assertThrows(
        IllegalArgumentException.class,
        () -> userService.createUser(user));

    }   
   
    @Test
    void naoDeveCriarUsuarioSemVaga() {
        UserModel user = new UserModel();
        user.setNome("Santo");
        user.setIdade(20);
        user.setVaga("");
        user.setObservacao("Candidato disponível");
    }

    @Test 
    void naoDeveCriarUsuarioSemDescricao() {
        UserModel user = new UserModel();
        user.setNome("Rafael");
        user.setIdade(25);
        user.setVaga("Desenvolvedor");
        user.setObservacao("");
    }
}