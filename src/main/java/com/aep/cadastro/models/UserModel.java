package com.aep.cadastro.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class UserModel {
    @Id
    private String id;
    private String nome;
    private Integer idade;
    private String vaga;
}
