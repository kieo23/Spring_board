package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//	@GetMapping("/sbb")
//
//	@ResponseBody // 단순 문자열 출력
//	public String indes() {
////		System.out.println("index");
//
//		// 프린트가 아닌 리턴으로 값을 주어야 출력
//		return "sbb 프로젝트 시작해 봅시다"; // "index" ---> 템플릿 파일 --> 공부....

	@GetMapping("/")
	public String root() {
		return "redirect:/question/list";

	}

}
