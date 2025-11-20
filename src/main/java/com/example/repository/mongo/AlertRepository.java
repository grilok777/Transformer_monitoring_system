package com.example.repository.mongo;

import com.example.entity.mongo.Alert;
import com.example.entity.mongo.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AlertRepository extends MongoRepository<Alert, Long> {


    Optional<Alert> findTopByOrderByIdDesc();
}