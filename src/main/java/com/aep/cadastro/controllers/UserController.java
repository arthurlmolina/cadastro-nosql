package com.aep.cadastro.controllers;

import ch.qos.logback.core.pattern.parser.FormattingNode;
import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;
import com.aep.cadastro.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private  UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> request = userService.getAllUsers();
    return  ResponseEntity.ok().body(request);
    }

    @GetMapping("/{id}")
    public Optional<UserModel> findById(@PathVariable String id){
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel){
        UserModel request = userService.createUser(userModel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(userModel.getId()).toUri();
        return ResponseEntity.created(uri).body(request);
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@RequestBody UserModel userModel, @PathVariable String id){
        return userService.updateUser(userModel, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
