package com.aep.cadastro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.aep.cadastro.CadastroApplication;
import com.aep.cadastro.controllers.UserController;
import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;

@SpringBootTest (classes = CadastroApplication.class)
public class UserControllerTests {

    @Autowired 
    private UserController userController;

    @Autowired 
    private UserRepository userRepository;

    @BeforeEach 
    void limparBanco() {
        userRepository.deleteAll();
    }

    @Test
    void deveBuscarTodosUsuarios() {

    UserModel user = new UserModel();
    user.setNome("João");
    user.setIdade(20);
    user.setVaga("Desenvolvedor");
    user.setObservacao("Disponível");

    userRepository.save(user);

    ResponseEntity<List<UserModel>> resposta =
            userController.findAll();

    assertEquals(200, resposta.getStatusCode().value());
    assertNotNull(resposta.getBody());
    assertEquals(1, resposta.getBody().size());
    assertEquals("João", resposta.getBody().get(0).getNome());
    }

    @Test 
    void deveBuscarUsuariosPorId() {
        UserModel user = new UserModel();
        user.setNome("Carlos");
        user.setIdade(25);
        user.setVaga("Analista");
        user.setObservacao("Dísponivel");

        UserModel salvo = userRepository.save(user);

        Optional<UserModel> resposta = userController.findById(salvo.getId());
        assertTrue(resposta.isPresent());
        assertEquals("Carlos", resposta.get().getNome());

    }

    @Test 
    void deveCriarUsuario() {
        UserModel user = new UserModel();
        user.setNome("Maria");
        user.setIdade(22);
        user.setVaga("Desenvolvedora");
        user.setObservacao("Primeiro");

        ResponseEntity<UserModel> resposta = userController.createUser(user);
    
        assertEquals(201, resposta.getStatusCode().value());
        assertNotNull(resposta.getBody());
        assertNotNull(resposta.getBody().getId());
        assertNotNull("Maria", resposta.getBody().getNome());
    }

    @Test 
    void deveAtualizarUsuario() {

        UserModel user = new UserModel();
        user.setNome("Pedro");
        user.setIdade(20);
        user.setVaga("Estagiário");
        user.setObservacao("Cadastro Inicial");

        UserModel salvo = userRepository.save(user);

        UserModel atualizado = new UserModel();
        atualizado.setNome("Pedro Silva");
        atualizado.setIdade(21);
        atualizado.setVaga("Desenvolvedor");
        atualizado.setObservacao("Atualizado");

        UserModel resposta = userController.updateUser(atualizado, salvo.getId());

        assertEquals("Pedro Silva", resposta.getNome());
        assertEquals(21, resposta.getIdade());
        assertEquals("Desenvolvedor", resposta.getVaga());
        assertEquals("Atualizado", resposta.getObservacao());
    }

    @Test 
    void deveDeletarUsuario() {
        UserModel user = new  UserModel();
        user.setNome("Ana");
        user.setIdade(30);
        user.setVaga("Analista");
        user.setObservacao("exclusão");

        UserModel salvo = userRepository.save(user);

        ResponseEntity<?> resposta = userController.deleteUser(salvo.getId());

        assertEquals(204, resposta.getStatusCode().value());
        assertFalse(userRepository.findById(salvo.getId()).isPresent());
    }

}
