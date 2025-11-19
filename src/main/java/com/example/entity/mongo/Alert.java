package com.example.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Alerts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Alert {
    @Id
    private Long id;

    private Long transformerId;

    private String message;

    private AlertLevel level;

    private Double temperature;

    private Double voltage;

    private String timestamp;

    private Integer problemResolved;
}