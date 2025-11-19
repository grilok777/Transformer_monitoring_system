package com.example.mapper;

import com.example.dto.TransformerDto;
import com.example.entity.mongo.Transformer;

public class TransformerMapper {

    public static TransformerDto toDto(Transformer transformer) {
        if (transformer == null) throw new NullPointerException();

        return new TransformerDto(
                transformer.getId(),
                transformer.getManufacturer(),
                transformer.getModelType(),
                transformer.getRatedPowerKVA(),
                transformer.getPrimaryVoltageKV(),
                transformer.getSecondaryVoltageKV(),
                transformer.getFrequencyHz(),
                transformer.getTransformerCondition(),
                transformer.getRemoteMonitoring(),
                transformer.getCurrentPower(),
                transformer.getCurrentTemperature(),
                transformer.getCurrentVoltage(),
                transformer.getStatus()
        );
    }
    public static Transformer toEntity(TransformerDto dto) {
        if (dto == null) throw new NullPointerException();

        return Transformer.builder()
                .id(dto.id())
                .manufacturer(dto.manufacturer())
                .modelType(dto.modelType())
                .ratedPowerKVA(dto.ratedPowerKVA())
                .primaryVoltageKV(dto.primaryVoltageKV())
                .secondaryVoltageKV(dto.secondaryVoltageKV())
                .frequencyHz(dto.frequencyHz())
                .transformerCondition(dto.transformerCondition())
                .remoteMonitoring(dto.remoteMonitoring())
                .currentPower(dto.currentPower())
                .currentTemperature(dto.currentTemperature())
                .currentVoltage(dto.currentVoltage())
                .status(dto.status())
                .build();
    }
}