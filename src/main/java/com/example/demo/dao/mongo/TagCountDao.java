package com.example.demo.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.bean.mongo.TagCount;

public interface TagCountDao extends MongoRepository<TagCount, String> {
}
