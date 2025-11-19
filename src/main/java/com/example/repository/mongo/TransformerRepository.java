package com.example.repository.mongo;

import com.example.entity.mongo.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TransformerRepository extends MongoRepository<Transformer, Long> {
}
