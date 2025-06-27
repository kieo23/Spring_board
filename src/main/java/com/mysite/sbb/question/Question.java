package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 추후 id --> null check --> Integer 사용, pk, id 순번은 자동 생성

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate; // 데이터 타입을 변환합니다

	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;

}
