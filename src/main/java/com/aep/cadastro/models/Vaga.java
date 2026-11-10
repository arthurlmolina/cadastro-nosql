package com.aep.cadastro.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/** Coleção "vagas". */
@Data
@Document(collection = "vagas")
public class Vaga {
    @Id
    private String id;
    private String titulo;
    private String empresa;
    private String descricao;
}
