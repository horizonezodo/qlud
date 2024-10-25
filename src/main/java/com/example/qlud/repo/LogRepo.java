package com.example.qlud.repo;

import com.example.qlud.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends MongoRepository<Log, String> {
    @Query("{ $or: [ " +
            "{ 'url': { $regex: ?0, $options: 'i' } }, " +
            "{ 'request': { $regex: ?0, $options: 'i' } }, " +
            "{ 'method': { $regex: ?0, $options: 'i' } }, " +
            "{ 'message': { $regex: ?0, $options: 'i' } }, " +
            "] }")
    List<Log> findByKey(String key);

    @Query(value = "{}", fields = "{ 'response': 0}")
    List<Log> findAllCustom();
}
