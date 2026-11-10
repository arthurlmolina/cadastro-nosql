package com.aep.cadastro.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/** Coleção "candidaturas". Liga um candidato a uma vaga guardando os ids dos dois. */
@Data
@Document(collection = "candidaturas")
public class Candidatura {
    @Id
    private String id;
    private String candidatoId;
    private String vagaId;
    private String observacao;
}
