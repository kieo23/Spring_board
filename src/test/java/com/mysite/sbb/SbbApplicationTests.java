package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	// test_Jpa() 대하여 DB 세션을 유지시킨다. 제일 마지막에 있는 test의 경우에서만 사용합니다.(다른것 테스트를 하려면 주석처리
	// 필요)
	// (모든 DB 작업이 성공해야 커밋되고, 하나라도 실패하면 모두 롤백)
	// 처음부터 순차적으로 test를 하려면 @@Transactional을 주석 처리하고 하세요
//	@Transactional
	@Test
	void testJpa() {
		// 질문 저장하기
//		Question q1 = new Question();
//		q1.setSubject("sbb가 도대체 무엇인가요?");
//		q1.setContent("sbb를 통해서 스프링부트 프로젝트를 공부하는거로 알고 있습니다. 좀더 알고 싶어요?");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1); // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2); // 두번째 질문 저장

		// findAll()
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(4, all.size());
//
//		Question q = all.get(0);
//		assertEquals("sbb가 도대체 무엇인가요?", q.getSubject());

		// findById(), Optional --> null 값을 유연하게 처리하기 위한 클래스, isPresent()로 값이 존재하는지 확인
		// --> True 값을 받음
		// 만약 값이 없으면 False 값 이 리턴됩니다
//		Optional<Question> op = this.questionRepository.findById(1);
//		if (op.isPresent()) {
//			Question q = op.get();
//			assertEquals("sbb가 도대체 무엇인가요?", q.getSubject());
//		}

		// findBySubject()
//		Question q = this.questionRepository.findBySubject("sbb가 도대체 무엇인가요?");
//		assertEquals(1, q.getId());

		// findBySubjectAndContent()
//		Question q = this.questionRepository.findBySubjectAndContent("스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?");
//		assertEquals(2, q.getId());

		// findBySubjectLike()
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb가 도대체 무엇인가요?", q.getSubject());

		// 질문 데이터 수정하기
//		Optional<Question> op = this.questionRepository.findById(1);
//		assertTrue(op.isPresent());
//		Question q = op.get();
//		q.setSubject("수정된 제목입니다");
//		this.questionRepository.save(q);

		// 질문 데이터 삭제하기
//		assertEquals(2, this.questionRepository.count());
//		Optional<Question> op = this.questionRepository.findById(1);
//		assertTrue(op.isPresent());
//		Question q = op.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());

		// 답변 데이터 저장하기 --> 어떤 질문의 답변인지...
//		Optional<Question> op = this.questionRepository.findById(2);
//		assertTrue(op.isPresent());
//		Question q = op.get();
//
//		Answer a = new Answer();
//		a.setContent("답변입니다");
//		a.setQuestion(q); // 어떤 질문의 답변인지, Question 객체가 필요함. --> question_id
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);

		// 답변 데이터 조회 하기
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId());

		// 답변 데이터를 통해 질문 데이터 찾기 vs '질문 데이터를 통해 답변 데이터 찾기'
//		Optional<Question> op = this.questionRepository.findById(2);
//		assertTrue(op.isPresent());
//		Question q = op.get();
//
//		List<Answer> answerList = q.getAnswerList(); // 여기까지 일반적으로 db 세션이 유지됨. 이후 끈어짐
//
//		assertEquals(1, answerList.size());
//		assertEquals("답변입니다", answerList.get(0).getContent());

		// 테스트 데이터 300개 생성

		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}

}
