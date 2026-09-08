package com.aep.cadastro.services;

import com.aep.cadastro.models.UserModel;
import com.aep.cadastro.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(String id){
        return userRepository.findById(id);
    }

    public UserModel createUser(UserModel user){
        return userRepository.save(user);
    }

    public UserModel updateUser(UserModel userModel, String id){
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
        BeanUtils.copyProperties(userModel, user, "id");
        return userRepository.save(user);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
