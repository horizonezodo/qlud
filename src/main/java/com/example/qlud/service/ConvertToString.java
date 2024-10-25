package com.example.qlud.service;

import com.example.qlud.requets.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConvertToString {
    private final ObjectMapper objectMapper;

    public ConvertToString() {
        this.objectMapper = new ObjectMapper();
    }

    public String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertMapToJson(Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
