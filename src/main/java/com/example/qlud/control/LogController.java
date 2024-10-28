package com.example.qlud.control;

import com.example.qlud.model.CustomLog;
import com.example.qlud.model.Log;
import com.example.qlud.repo.LogRepo;
import com.example.qlud.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogController {

    @Autowired
    private LogRepo repo;

    @GetMapping("/all-log")
    public ResponseEntity<?> getAllLog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size){
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        return new ResponseEntity<>(repo.findAllCustom(pageable), HttpStatus.OK);
    }

    @GetMapping("/log/{id}")
    public ResponseEntity<?> getLogById(@PathVariable("id")String id){
        Optional<Log> opt = repo.findById(id);
        if(opt.isPresent()){
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MessageResponse("Cannot find this id: " + id), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search-log")
    public ResponseEntity<?> searchLog(@RequestParam("key")String key,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size){
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        return new ResponseEntity<>(repo.findByKey(key,pageable), HttpStatus.OK);
    }
}
