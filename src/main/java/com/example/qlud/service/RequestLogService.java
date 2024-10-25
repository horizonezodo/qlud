package com.example.qlud.service;

import com.example.qlud.model.Log;
import com.example.qlud.repo.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RequestLogService {

    @Autowired
    LogRepo repo;

    public void saveLog(String url, String method, String requestBody, String responseBody, int responseStatus, Instant startTime){
        Log log = new Log();
        log.setUrl(url);
        log.setMethod(method);
        log.setRequest(requestBody);
        log.setResponse(responseBody);
        log.setStatus(responseStatus);
        log.setStartTime(startTime);
        repo.save(log);
    }
}
