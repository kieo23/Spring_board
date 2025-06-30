package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public List<Question> getList() {
		return this.questionRepository.findAll();

	}

	public Question getQuestion(Integer id) {

		// 브라우져에서 없는 id값을 입력 할 경우 예외 처리함
		// optional은 NUll 처리를 안전하게 할수 있도록 도와주는 class 입니다
		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}

	}

	public void create(String subject, String content) {
		Question q = new Question();
		q.setSubject(subject);
		q.setSubject(content);
		q.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q);

	}
}