package com.example.qlud.control;

import com.example.qlud.model.DataCrawl;
import com.example.qlud.repo.DataCrawlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlController {

    @Autowired
    DataCrawlRepo repo;

    @PostMapping("/crawl-data")
    public ResponseEntity<?> test(){
        DataCrawl dataCrawl = new DataCrawl();
        dataCrawl.setWebsiteUrl("http://example.com");
        dataCrawl.setImageUrl("http://example.com/image.jpg");
        dataCrawl.setTitle("Example Title");
        dataCrawl.setDescription("Example Description");
        dataCrawl.setProductAttribute("Example Attribute");

        repo.save(dataCrawl);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
