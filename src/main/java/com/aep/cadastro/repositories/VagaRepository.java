package com.aep.cadastro.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aep.cadastro.models.Vaga;

public interface VagaRepository extends MongoRepository<Vaga, String> {
}
