package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		
		User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
			  .orElseThrow(() -> 
		  		 new UsernameNotFoundException("User not found with username or email: "+userNameOrEmail));
		
		Set<GrantedAuthority> authorites = user.getRoles()
											.stream()
											.map((role) -> new SimpleGrantedAuthority(role.getName()))
											.collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), authorites);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
