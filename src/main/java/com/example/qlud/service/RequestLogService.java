package com.example.qlud.service;

import com.example.qlud.model.LogData;
import com.example.qlud.repo.LogDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RequestLogService {

    @Autowired
    LogDataRepo repo;

    public void saveLog(String url, String method, String requestBody, String responseBody, int responseStatus, Instant startTime, Instant endTime, long duration){
        LogData logData = new LogData();
        logData.setUrl(url);
        logData.setMethod(method);
        logData.setRequest(requestBody);
        logData.setResponse(responseBody);
        logData.setStatus(responseStatus);
        logData.setStartTime(startTime);
        logData.setEndTime(endTime);
        logData.setDuration(duration);
        repo.save(logData);
    }
}
