package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

//레파지토리 생성
public interface UserRepository extends JpaRepository<SiteUser, Long> {

}
