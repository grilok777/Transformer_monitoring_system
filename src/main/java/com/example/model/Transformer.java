package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "Transformers")
@Setter
@Getter
public class Transformer {

    @Id
    private Long id; //Long

    private String manufacturer;

    private String modelType;

    private Double ratedPowerKVA;

    private Integer primaryVoltageKV;

    private Integer secondaryVoltageKV;

    private Double frequencyHz;

    private Boolean transformerCondition;

    private Boolean remoteMonitoring;

    private Double currentPower;

    private Double currentTemperature;

    private Double currentVoltage;

    private TransformerStatus status = TransformerStatus.NORMAL;

    private List<Map<String, Object>> dataLogs = new ArrayList<>();

    public Transformer() {}

}
