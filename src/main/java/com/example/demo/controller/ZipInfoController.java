package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ZipInfoService;

@RestController
@RequestMapping("/zipInfo")
public class ZipInfoController {

	@Autowired
	private ZipInfoService zipInfoService;

	@RequestMapping("/init")
	public Object init() {
		return zipInfoService.init();
	}
	@RequestMapping("/aggregation1")
	public Object aggregation1() {
		return zipInfoService.aggregation1();
	}
	@RequestMapping("/aggregation2")
	public Object aggregation2() {
		return zipInfoService.aggregation2();
	}
	@RequestMapping("/aggregation3")
	public Object aggregation3() {
		return zipInfoService.aggregation3();
	}

}
