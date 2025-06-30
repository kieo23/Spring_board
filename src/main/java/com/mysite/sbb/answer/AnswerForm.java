package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	// 질문은 (subject)그대로 가져오며 답변은 Answer 고유의 concent를 사용한다

	@NotEmpty(message = "내용은 필수항목입니다.")
	private String content;

}
