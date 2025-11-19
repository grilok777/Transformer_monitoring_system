package com.example.service.interfaces;


import com.example.entity.mongo.Transformer;
import com.example.entity.mongo.TransformerStatus;

import java.util.List;
import java.util.Optional;

public interface TransformerService {

    Optional<Transformer> getById(Long id);

    List<Transformer> getAll();

    Transformer save(Transformer transformer);

    TransformerStatus checkStatus(Long transformerId);

    void updateData(Long id, Double power, Double temperature, Double voltage);

    void updateStatus(Long transformerId, Double temperature, Double voltage);
}
