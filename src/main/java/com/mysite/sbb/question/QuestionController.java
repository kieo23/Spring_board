package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

// @RequestMapping을 이용해서 프리픽스(prefix 접두사) 지정해서 사용
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// Controller 에 있는 repository 기능을 모두 service 에게 전달
	private final QuestionService questionService;

	// 질문 목록
	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList); // db --> model 저장
		return "question_list"; // question_list.html에서 model 접근해서 사용

	}

	// 상세조회
	// @PathVariable("id") Integer id
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";

	}

	// 질문 등록 메서드
	@GetMapping("/create")
	public String questionCreate() {
		return "question_form";
	}

	@PostMapping("/create")
	public String questionCreate(@RequestParam(value = "subject") String subject,
			@RequestParam(value = "content") String content) {
		// Service에서 처리 할 것
		this.questionService.create(subject, content);
		return "redirect:/question/list"; // 질문 저장 후 질문목록으로 이동
	}
}