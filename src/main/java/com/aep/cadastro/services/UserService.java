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
        if (user.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (user.getIdade() <= 0) {
            throw new IllegalArgumentException("Idade deve ser maior que zero");
        }
        if (user.getVaga().isBlank()) {
            throw new IllegalArgumentException("Vaga é obrigatoória");
        }
        if (user.getObservacao().isBlank()) {
            throw new IllegalArgumentException("Descrião é obrigatória");
        }

        return userRepository.save(user);
    }

    public UserModel updateUser(UserModel userModel, String id){
        if (userModel.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (userModel.getIdade() <= 0) {
            throw new IllegalArgumentException("Idade deve ser maior que zero");
        }
        if (userModel.getVaga().isBlank()) {
            throw new IllegalArgumentException("Vaga é obrigatória");
        }
        if (userModel.getObservacao().isBlank()) {
            throw new IllegalArgumentException("Obsevação é obrigatória");
        }
        UserModel user = userRepository.findById(id).get();
        BeanUtils.copyProperties(userModel, user);
        return userRepository.save(user);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
