package com.example.qlud.repo;

import com.example.qlud.model.DataCrawl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCrawlRepo extends MongoRepository<DataCrawl, String> {
}
