package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@Column(unique = true)
	private String email;

	// 위 아래 내역은 내가 Ai에게 물어본것으로
	// 나중에 다시 확인할것
//	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Question> questionList;
//
//	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Answer> answerList;
}
