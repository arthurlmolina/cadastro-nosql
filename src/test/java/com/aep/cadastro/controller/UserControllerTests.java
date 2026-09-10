package com.aep.cadastro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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

}
