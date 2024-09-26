package com.example.qlud.repo;

import com.example.qlud.model.DataCrawl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCrawlRepo extends JpaRepository<DataCrawl, Long> {
}
