package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogData {
    @Id
    private String id;
    private String url;
    private String request;
    private String response;
    private Instant startTime;
    private Instant endTime;
    private long duration;
    private int status;
    private String method;
    private String logLevel;
}
