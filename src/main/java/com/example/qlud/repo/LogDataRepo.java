package com.example.qlud.repo;

import com.example.qlud.model.LogData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDataRepo extends MongoRepository<LogData, String> {

}
