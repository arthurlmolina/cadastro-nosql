package com.aep.cadastro.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/** Coleção "candidatos". Possui uma lista de enderecos (subdocumentos). */
@Data
@Document(collection = "candidatos")
public class Candidato {
    @Id
    private String id;
    private String nome;
    private String email;
    private Integer idade;
    private List<Endereco> enderecos = new ArrayList<>();
}
