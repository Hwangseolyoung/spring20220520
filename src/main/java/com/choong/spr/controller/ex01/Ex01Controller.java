package com.choong.spr.controller.ex01;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.choong.spr.domain.ex01.Cartegory;
import com.choong.spr.domain.ex01.Products;
import com.choong.spr.mapper.ex01.Ex01Mapper;

@Controller
@RequestMapping("ex01")
public class Ex01Controller {
	
	@Autowired
	private Ex01Mapper mapper;
	
	// 체크박스를 선택하지 않을 수 있으니 required = false로 설정해주기
	// required = false 설정하지 않으면 기본 출력 리스트 오류발생
	// 체크박스에서 1개이상 선택할 수 있으므로 파라미터 List<타입명>로 받아야한다.
	@RequestMapping("sub01") 
	public void listProducts(@RequestParam(name="category", required = false) List<Integer> category, 
							Model model) {
		
		System.out.println(category);
		
		// products list 출력
		List<Cartegory> categoryList = mapper.selectCategory();
		
		// category 선택 기준으로 products 정렬하기
		// 파라미터로 받은 categoryID로 조회한 결과 리스트에 담아주기
		List<Products> list = mapper.selectProducts(category);
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("list", list);
	}
	
	@RequestMapping("sub02")
	public void method(String price, Model model) {
		
		// 특정 가격 조건으로 조회해서 출력하기
		List<Products> list = mapper.selectProductsThen(price);
		
		model.addAttribute("list", list);
		
	}
	
	
	
}
