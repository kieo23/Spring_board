package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// @RequestMapping을 이용해서 프리픽스(prefix 접두사) 지정해서 사용
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// Controller 에 있는 repository 기능을 모두 service 에게 전달
	private final QuestionService questionService;
	// 서비스 객체 추가
	// DI (객체 주입) --> UserService 추가
	private final UserService userService;

	// 질문 목록
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question_list";

	}

	// 상세조회
	// @PathVariable("id") Integer id
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";

	}

	// @PreAuthorize("isAuthenticated()") --> 인증된(로그인한) 사용자일 경우 작동
	// 로그인 안된경우, 로그인 페이지로 강제 이동함
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}

	// 스프링 시큐리티 Principal 객체를 매개변수 선언
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String create(@Valid QuestionForm questionform, BindingResult result, Principal principal) {
		if (result.hasErrors())
			return "question_form";
		// principal 객체를 통해서 사용자명 얻고, siteUser 객체를 얻음
		SiteUser siteuser = this.userService.getUser(principal.getName());
		// 질문 등록 될 떄 siteUser(글쓴이 id)저장
		this.questionService.create(questionform.getSubject(), questionform.getContent(), siteuser);
		return "redirect:/question/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionCreate(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {

		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}

}