package com.aep.cadastro.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aep.cadastro.models.Candidato;

public interface CandidatoRepository extends MongoRepository<Candidato, String> {
}
