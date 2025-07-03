package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);

		// 사용자가 비어있다면
		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		// 사용자가 존재한다면 정보를 siteUser 객체에 담는다
		SiteUser siteUser = _siteUser.get();

		// 권한 부여를 위한 준비를 하며 여러개의 권한이 부여 될 수 있기에
		// ArrayList로 칸수제한 없는 List를 선언
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 유저명이 admin이라면 관리자 권한 부여
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			// 그렇지 않다면 일반 사용자 권한 부여
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		// 사용자 객체를 만들어서 저장하고 권한을 인가해줌
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}

}
