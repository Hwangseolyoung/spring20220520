package com.choong.spr.controller.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ex02.Book;

/*
// controller에 붙여주면 전체 메소드가 응답본문 자체를 전달함
@Controller
@ResponseBody
*/

// @Controller, @ResponseBody 두가지 일을 동시에 해준다
@RestController 
@RequestMapping("ex03")
public class Ex03Controller {
	
	@RequestMapping("sub01")
	public String method01() {
		return "string data";
	}
	
	@RequestMapping("sub02")
	public Book method02() {
		
		Book b = new Book();
		b.setTitle("soccer");
		b.setWriter("jimin");
		
		return b;
	}
	
	@RequestMapping("sub03")
	public String method03() {
		System.out.println("ex03/sub03 일함!!");
		
		return "hello ajax";
	}
	
	@RequestMapping("sub04")
	public String method04() {
		System.out.println("ex03/sub04 일함!!");
		
		return "hello ajax2";
	}
	
	@GetMapping("sub05")
	public String method05() {
		System.out.println("ex03/sub05 get 방식 일함!!");
		
		return "hello ajax3";
	}
	
	@PostMapping("sub06")
	public String method06() {
		System.out.println("ex03/sub06 post 방식 일함!!");
		
		return "hello ajax4";
	}
	
	@DeleteMapping("sub07")
	public String method07() {
		System.out.println("ex03/sub07 delete 방식 일함!!");
		
		return "hello ajax5";
	}
	
	@PutMapping("sub08")
	public String method08() {
		System.out.println("ex03/sub08 put 방식 일함!!");
		
		return "hello ajax6";
	}
	
	@GetMapping("sub09")
	public String method09(String title, String writer) {
		System.out.println("#받은 데이터");
		System.out.println("title:" + title);
		System.out.println("writer:" + writer);
		
		return null;
	}
	
	@PostMapping("sub10")
	public String method10(String name, String address) {
		System.out.println("#받은 데이터");
		System.out.println("name : " + name);
		System.out.println("address : " + address);
		
		return "good!";
	}
	
	@PostMapping("sub11")
	public void method11(Book book) {
		System.out.println(book);
		
	}
	
	@PostMapping("sub12")
	public String method12() {
		System.out.println("12번째 메소드 일함");
		
		return "hi";
	}
	
	@GetMapping("sub13")
	public Integer method13() {
		return (int) (Math.random() * 45 + 1);
	}
	
	@GetMapping("sub14")
	public Book method14() {
		Book b = new Book();
		b.setTitle("스프링");
		b.setWriter("김자반");
		
		return b;
	}
	
	@GetMapping("sub15")
	public Map<String, String> method15() {
		Map<String, String> map = new HashMap<>();
		map.put("name", "손흥민");
		map.put("age", "30");
		map.put("address", "london");
		
		return map;
		
	}
	// ResponseEntity : 에러응답 코드 구현 메소드
	// @ResponseBody 어노테이션과 같이 사용할 수 있으며
	// 본문이 타입파라미터에 들어가면됨
	@GetMapping("sub17")
	public ResponseEntity<String> methode17() {
		// 콘솔에 GET http://localhost:8080/spr2/ex03/sub17 500 찍힘
		return ResponseEntity.status(500).body("internal server error");
	}
	/*
	@GetMapping("sub18")
	public ResponseEntity<String> methode17() {
		boolean success = service.
	}
	*/
	
	@GetMapping("sub18")
	public ResponseEntity<String> method18() {
		boolean success = Math.random() > 0.5;
		
		if (success) {
			return ResponseEntity.ok().body("data you want");
		} else {
			return ResponseEntity.status(500).body("something wrong");
		}
	}
}
