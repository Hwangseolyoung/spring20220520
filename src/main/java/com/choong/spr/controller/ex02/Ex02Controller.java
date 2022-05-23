package com.choong.spr.controller.ex02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choong.spr.domain.ex02.Book;

@Controller
@RequestMapping("ex02")
public class Ex02Controller {
	
	@RequestMapping("sub01")
	public String method01() {
		
		// json 데이터 응답받기(String 데이터)
		// xml형식의 view로 해석되는 것이 아니라 전달하는 data(string)으로 해석되기
		return "hello";
	}
	
	@RequestMapping("sub02")
	// @ResponseBody : 응답본문 어노테이션(데이터 자체로 해석하게됨)
	@ResponseBody 
	public String method02() {
		
		return "hello";
	}
	
	@RequestMapping("sub03")
	@ResponseBody
	public String method03() {
		
		return "{\"title\" : \"java\", \"writer\" : \"son\"}"; // json 형식 데이터 String
	}
	
	@RequestMapping("sub04")
	@ResponseBody
	// json String 객체 자체를 넘겨주기
	public Book method04() {
		
		Book b = new Book();
		b.setTitle("spring");
		b.setWriter("son");
		
		
		return b;
		
		
	}
	
	@RequestMapping("sub05")
	public String method05() {
		
		return "/ex03/sub01";
	}
	
	/*
	@RequestMapping("sub06")
	public String method06() {
		return "/ex03/sub01";
	}
	*/
	
	
}
