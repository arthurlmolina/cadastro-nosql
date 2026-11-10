package com.aep.cadastro.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Objeto aninhado dentro do documento do candidato. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String rua;
    private String cidade;
    private String estado;
}
