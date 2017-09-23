package com.example.demo.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.bean.mongo.InventoryItem;

public interface InventoryItemDao extends MongoRepository<InventoryItem, Integer> {
}
