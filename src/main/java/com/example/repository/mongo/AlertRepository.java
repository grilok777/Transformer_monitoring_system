package com.example.repository.mongo;

import com.example.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends MongoRepository<Alert, Long> {//String

    List<Alert> findByTransformerId(Long transformerId);//String

    Optional<Alert> findTopByOrderByIdDesc();
}
