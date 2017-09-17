package com.example.demo.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import com.example.demo.bean.mongo.QUser;
import com.example.demo.bean.mongo.User;

public interface UserDao extends MongoRepository<User, String>,QuerydslPredicateExecutor<User>,QuerydslBinderCustomizer<QUser> {

	/**
	 * 结合MVC的@QuerydslPredicate使用,修改“name”用like查询
	 */
	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(root.name).first((path,value)->path.contains(value));
	}
//public interface UserDao extends MongoRepository<User, String> {

}
