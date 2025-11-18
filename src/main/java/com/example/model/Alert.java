package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Alerts")
@Setter
@Getter
public class Alert {

    @Id
    private Long id; //Long

    private Long transformerId; //Long

    private String message;


    private AlertLevel level;


    private Double temperature;

    private Double voltage;

    private String timestamp;


    private int  problemResolved;

    public Alert() {}

    public Alert(Long id, Long tId, String message, AlertLevel level, Double temp, Double volt, String timestamp, int problemResolved) {
        this.id = id;
        this.transformerId = tId;
        this.message = message;
        this.level = level;
        this.temperature =  temp;
        this.voltage = volt;
        this.timestamp = timestamp;
        this.problemResolved = problemResolved;
    }



}
