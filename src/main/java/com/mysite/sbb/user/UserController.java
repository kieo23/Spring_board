package com.mysite.sbb.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // DI를 사용하기 위해
@Controller // 컨트롤러 사용요청
@RequestMapping("/user") // url 매칭시킴
public class UserController {

	private final UserService userService;

	@GetMapping("/signup")
	public String signup(UserCreateForm usercreateForm) {
		return "signup_form";
	}

}
