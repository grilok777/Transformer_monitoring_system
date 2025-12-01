package com.example.repository.mongo;

import com.example.entity.mongo.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransformerRepository extends MongoRepository<Transformer, Long> {
    Optional<Transformer> findTopByOrderByIdDesc();
}