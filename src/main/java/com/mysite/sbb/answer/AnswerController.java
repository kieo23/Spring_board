package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	// DI (객체 주입, 아래 2개 객체 주입)
	private final QuestionService questionService;
	private final AnswerService answerService;

	// 추가된 @RequestParam(value = "content") String content -->
	// (question_detail.html)에서 답변으로 입력한 내용(content)을 사용하기 위함
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "content") String content) {
		Question question = this.questionService.getQuestion(id);
		// TODO: 답변을 저장한다.
		this.answerService.create(question, content);
		return String.format("redirect:/question/detail/%s", id);
	}
}