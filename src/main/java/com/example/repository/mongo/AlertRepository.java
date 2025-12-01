package com.example.repository.mongo;

import com.example.entity.mongo.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends MongoRepository<Alert, Long> {

    List<Alert> findByTransformerId(Long transformerId);//String

    Optional<Alert> findTopByOrderByIdDesc();
}