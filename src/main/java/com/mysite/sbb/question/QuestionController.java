package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionRepository questionRepository;

	@GetMapping("/question/list")
	public String list(Model model) {
		List<Question> questionList = this.questionRepository.findAll();
		model.addAttribute("questionList", questionList); // db --> model 저장
		return "question_list"; // question_list.html에서 model 접근해서 사용

	}

	@GetMapping(value = "/question/detail/{id}")
	// 변하는 값은 { } 로 따로 지정
	public String detail(Model model) {
		return "question_dateil";
	}

}
