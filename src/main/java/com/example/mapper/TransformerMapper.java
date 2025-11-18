package com.example.mapper;

import com.example.dto.TransformerDto;
import com.example.model.Transformer;

public class TransformerMapper {

    public static TransformerDto toDto(Transformer t) {
        return new TransformerDto(
                t.getId(),
                t.getManufacturer(),
                t.getModelType(),
                t.getRatedPowerKVA(),
                t.getPrimaryVoltageKV(),
                t.getSecondaryVoltageKV(),
                t.getFrequencyHz(),
                t.getTransformerCondition(),
                t.getRemoteMonitoring(),
                t.getStatus().name()
        );
    }
}
