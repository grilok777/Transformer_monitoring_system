package com.example.repository;

import com.example.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AlertRepository extends MongoRepository<Alert, String> {


    Optional<Alert> findTopByOrderByIdDesc();
}
