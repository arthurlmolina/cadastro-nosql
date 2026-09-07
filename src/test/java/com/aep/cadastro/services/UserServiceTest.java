package com.aep.cadastro.services;

import com.aep.cadastro.CadastroApplication;
import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void deveCriarUsuario() {
        UserModel user = new UserModel();
        user.setNome("João");
        user.setIdade(20);
        user.setVaga("Desenvolvedor");

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

        userService.createUser(user);

        List<UserModel> usuarios = userService.getAllUsers();

        assertEquals(1, usuarios.size());
        assertEquals("Maria", usuarios.get(0).getNome());
    }
}