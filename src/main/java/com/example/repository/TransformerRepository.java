package com.example.repository;

import com.example.model.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransformerRepository extends MongoRepository<Transformer, String> {
}
