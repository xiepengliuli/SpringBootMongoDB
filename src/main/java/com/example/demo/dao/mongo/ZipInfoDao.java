package com.example.demo.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.bean.mongo.ZipInfo;

public interface ZipInfoDao extends MongoRepository<ZipInfo, String> {
}
