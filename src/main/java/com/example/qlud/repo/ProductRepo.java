package com.example.qlud.repo;

import com.example.qlud.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

}
