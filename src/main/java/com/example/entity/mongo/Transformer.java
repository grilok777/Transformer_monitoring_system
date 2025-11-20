package com.example.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "Transformers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transformer {

    @Id
    private Long id;  // ← ПОВИННО бути Long

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

    private TransformerStatus status;

    private List<Map<String, Object>> dataLogs = new ArrayList<>();

}