package com.example.repository.mongo;

import com.example.entity.mongo.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransformerRepository extends MongoRepository<Transformer, Long> {
    Optional<Transformer> findTopByOrderByIdDesc();//String
}
