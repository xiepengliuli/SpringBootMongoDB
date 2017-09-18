package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.mongo.User;
import com.example.demo.dao.mongo.UserDao;
import com.example.demo.service.UserService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	UserDao userDao;

	@RequestMapping("/findAll1")
	public Object findAll1() {
		Page findAll1 = userService.findAll1();
		return findAll1;
	}

	@RequestMapping("/findAll2")
	public Object findAll2() {
		Object findAll2 = userService.findAll2();
		return findAll2;
	}

	@RequestMapping("/findAll3")
	public Object findAll3() {
		Object findAll3 = userService.findAll3();
		return findAll3;
	}

	@RequestMapping("/findAll4")
	public Object findAll4() {
		Object findAll4 = userService.findAll4();
		return findAll4;
	}

	@RequestMapping("/findAll5")
	public Object findAll5() {
		Object findAll5 = userService.findAll5();
		return findAll5;
	}

	@RequestMapping("/findAll6")
	public Object findAll6() {
		Object findAll6 = userService.findAll6();
		return findAll6;
	}

	@RequestMapping("/findAll7")
	public Page<User> findAll7() {
		Page<User> findAll7 = userService.findAll7();
		return findAll7;
	}

	@RequestMapping("/findAll8")
	public Page<User> findAll8() {
		Page<User> findAll8 = userService.findAll8();
		return findAll8;
	}

	@RequestMapping("/findAll9")
	public Object findAll9() {
		Object findAll9 = userService.findAll9();
		return findAll9;
	}

	@RequestMapping("/findAll10")
	public Object findAll10() {
		Object findAll10 = userService.findAll10();
		return findAll10;
	}

	// name=英富森1&id=1&sort=name,desc
	//UserDao中使用了自定义绑定
	@RequestMapping(value = "/findAll11")
	Object findAll11(Model model, @QuerydslPredicate(root = User.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam MultiValueMap<String, String> parameters) {
		System.out.println("aaaa");
		// Page<User> findAll = userDao.findAll( pageable);
		Page<User> findAll = userDao.findAll(predicate, pageable);
		// model.addAttribute("users", userDao.findAll(predicate, pageable));
		return findAll;
	}
}
