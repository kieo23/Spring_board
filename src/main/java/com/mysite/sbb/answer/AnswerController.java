package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	// DI (객체 주입, 아래 2개 객체 주입)
	private final QuestionService questionService;
	private final AnswerService answerService;
	// DI UserService 추가
	private final UserService userService;

	// @PreAuthorize("isAuthenticated()") --> 로그인이 안될경우만 실행
	// 로그인 안된경우, 로그인 페이지로 강제 이동함
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal) {
		// Principal 사용함으로써 사용자가
		// 누구인지 확인가능
		Question quesion = this.questionService.getQuestion(id);
		// principal 객체를 통해서 사용자명 얻고, siteUser 객체를 얻음
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", quesion);
			return "question_detail";
		}
		// 답변 등록 될때 siteUser(author_id)글쓴이 id 를 저장한다
		this.answerService.create(quesion, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}

	// 객체 수정을 할시 "Get"방식으로 화면을 넘겨줌
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {

		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent());
		return "answer_form";
	}

	// 객체 수정이 끝나고 "Post"방식으로 저장값을 서버(DB)에 넘겨줌
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.answerService.delete(answer);
		return "redirect:/";
	}
}