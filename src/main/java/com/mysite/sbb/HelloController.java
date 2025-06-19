package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 이 파일을 컨트롤러로 사용 -> 오더내림
//HelloController <-- 조종기
//내장된 SDD는 기본적으로 자동저장(새로고침)해도 안바뀜
public class HelloController {

	@GetMapping("/hello") // url 매핑 -> 주소 지정
							// 메서드로 작동 hello 를 치면 hello world가 출력되도록 담아둠
							// 매핑 hello
	@ResponseBody
	public String hello() {
		return "Hello Wrold ~~123~~"; // body에 들어감 -> 원래는 주소가 들어가서 보여줌 getresponsdispanser
	}

}
