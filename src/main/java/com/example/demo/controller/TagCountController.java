package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TagCountService;

@RestController
@RequestMapping("/tagCount")
public class TagCountController {

	@Autowired
	private TagCountService tagCountService;

	@RequestMapping("/aggregation1")
	public Object aggregation1() {
		return tagCountService.aggregation1();
	}
	@RequestMapping("/aggregation2")
	public Object aggregation2() {
		return tagCountService.aggregation2();
	}
	@RequestMapping("/aggregation3")
	public Object aggregation3() {
		return tagCountService.aggregation3();
	}

}
