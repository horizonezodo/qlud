package com.example.qlud.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CustomLog {
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${default.server}")
    private String defaultServer;

    public String info(String message, String request, String url, String respose, String method, Instant startTime,int status){
        Log log = new Log();
        log.setLogLevel("INFO");
        log.setMessage(message);
        log.setMethod(method);
        log.setStatus(status);
        log.setStartTime(startTime);
        log.setRequest(request);
        log.setResponse(respose);
        log.setUrl(defaultServer + url);
        mongoTemplate.insert(log);
        return message;
    }

    public String warn(String message, String request, String url, String respose, String method, Instant startTime,int status){
        Log log = new Log();
        log.setLogLevel("WARN");
        log.setMessage(message);
        log.setMethod(method);
        log.setStatus(status);
        log.setStartTime(startTime);
        log.setRequest(request);
        log.setResponse(respose);
        log.setUrl(defaultServer + url);
        mongoTemplate.insert(log);
        return message;
    }

    public String error(String message, String request, String url, String respose, String method, Instant startTime,int status){
        Log log = new Log();
        log.setLogLevel("ERROR");
        log.setMessage(message);
        log.setMethod(method);
        log.setStatus(status);
        log.setStartTime(startTime);
        log.setRequest(request);
        log.setResponse(respose);
        log.setUrl(defaultServer + url);
        mongoTemplate.insert(log);
        return message;
    }
}
