package com.aep.cadastro.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aep.cadastro.models.Candidatura;

public interface CandidaturaRepository extends MongoRepository<Candidatura, String> {
    List<Candidatura> findByCandidatoId(String candidatoId);

    void deleteByCandidatoId(String candidatoId);

    void deleteByVagaId(String vagaId);
}
