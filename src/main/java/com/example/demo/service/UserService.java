package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.mongo.QUser;
import com.example.demo.bean.mongo.User;
import com.example.demo.dao.mongo.UserDao;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private MongoOperations mongoOperations;

	/**
	 * 坑
	 * userDao.findAll(qPageRequest);//接口不能正常使用,对PagingAndSortingRepository的支持没实现,如果用mvc接参Pageable则可正常执行
	 *
	 * @return
	 */
	public Page findAll1() {
		System.out.println(mongoOperations);
		PageRequest qPageRequest = new PageRequest(0, 2);

		Page<User> findAll = userDao.findAll(qPageRequest);// 接口不能正常使用,对PagingAndSortingRepository的支持没实现

		if (findAll.getContent().size() <= 0) {
			initData();
			findAll = userDao.findAll(qPageRequest);// 接口不能正常使用,对PagingAndSortingRepository的支持没实现
		}
		return findAll;
	}

	public Object findAll2() {
		// Pageable pageable = new PageRequest(0, 10, new QSort(new
		// OrderSpecifier<>(Order.DESC, QEmployee.employee.salary), new
		// OrderSpecifier<>(Order.ASC, QDepartment.department.name)));
		OrderSpecifier<?> aaa = new OrderSpecifier<>(Order.ASC, QUser.user.age);
		QSort sort = new QSort(aaa);
		List<User> findAll = userDao.findAll(sort);

		return findAll;
	}

	public Object findAll3() {
		User user = new User();
		user.setName("英富森2");
		Example<User> example = Example.of(user);

		List<User> findAll = userDao.findAll(example);

		return findAll;
	}

	public Object findAll4() {
		User user = new User();
		user.setName("1");

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", match -> match.contains());

		Example<User> example = Example.of(user, exampleMatcher);

		List<User> findAll = userDao.findAll(example);

		return findAll;
	}

	public Object findAll5() {
		User user = new User();
		user.setName("英富森2");

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("lastname")//
				.withIncludeNullValues()//
				.withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING)// 全局设置默认使用like
				.withMatcher("name", matcher -> matcher.stringMatcher(StringMatcher.EXACT))// 此属性使用=
		// .withMatcher("name", GenericPropertyMatchers.exact())//此属性使用=
		// .withMatcher("name", matcher -> matcher.exact())// 此属性使用=

		;

		Example<User> example = Example.of(user, exampleMatcher);

		List<User> findAll = userDao.findAll(example);

		return findAll;
	}

	public Object findAll6() {
		User user = new User();
		user.setName("英富森2");

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("lastname")//
				.withIncludeNullValues()//
				.withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING)// 全局设置默认使用like
				.withMatcher("name", matcher -> matcher.stringMatcher(StringMatcher.CONTAINING))//
		;
		Example<User> example = Example.of(user, exampleMatcher);

		OrderSpecifier<?> aaa = new OrderSpecifier<>(Order.ASC, QUser.user.age);
		QSort sort = new QSort(aaa);

		List<User> findAll = userDao.findAll(example, sort);

		return findAll;
	}

	public Page<User> findAll7() {
		User user = new User();
		user.setName("英富森2");

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("lastname")//
				.withIncludeNullValues()//
				.withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING);
		Example<User> example = Example.of(user, exampleMatcher);

		Sort sort1 = new Sort(Direction.ASC, "age");
		Pageable pageable2 = new PageRequest(0, 2, sort1);

		Page<User> findAll = userDao.findAll(example, pageable2);
		List<User> content = findAll.getContent();
		for (User user2 : content) {
			System.out.println(user2);
		}

		return findAll;
	}

	/**
	 * 坑 QPageRequest接口不太好使,return Page的時候报错
	 * 
	 * @return
	 */
	public Page<User> findAll8() {
		User user = new User();
		user.setName("英富森2");

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("lastname")//
				.withIncludeNullValues()//
				.withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING);
		Example<User> example = Example.of(user, exampleMatcher);

		// QPageRequest接口不太好使,return Page的時候报错
		OrderSpecifier<?> aaa = new OrderSpecifier<>(Order.DESC, QUser.user.age);
		QSort sort = new QSort(aaa);
		Pageable pageable = new QPageRequest(0, 2, sort);
		Page<User> findAll = userDao.findAll(example, pageable);
		List<User> content = findAll.getContent();
		for (User user2 : content) {
			System.out.println(user2);
		}

		return findAll;
	}

	public Object findAll9() {
		// QPageRequest接口不太好使,return Page的時候报错
		OrderSpecifier<?> aaa = new OrderSpecifier<>(Order.DESC, QUser.user.age);
		Iterable<User> findAll2 = userDao.findAll(aaa);
		return findAll2;
	}

	public Object findAll10() {
		BooleanExpression between = QUser.user.age.between(0, 20);
		between.and(QUser.user.name.contains("1"));

		Predicate predicate = between;
		Iterable<User> findAll = userDao.findAll(predicate);
		return findAll;
	}

	@Transactional
	private void initData() {
		ArrayList<User> arrayList = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setId(i + "");
			user.setAge(i);
			user.setBirthDay(new Date());
			user.setName("英富森" + i);
			arrayList.add(user);
		}
		userDao.saveAll(arrayList);

	}

}
