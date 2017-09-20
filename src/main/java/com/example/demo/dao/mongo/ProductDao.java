package com.example.demo.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.bean.mongo.Product;

public interface ProductDao extends MongoRepository<Product, String> {
}
