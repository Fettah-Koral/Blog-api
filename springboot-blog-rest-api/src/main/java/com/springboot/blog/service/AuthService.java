package com.springboot.blog.service;

import com.springboot.blog.dtos.LoginDto;
import com.springboot.blog.dtos.RegisterDto;

public interface AuthService {
	
	String login(LoginDto loginDto);

	String Register(RegisterDto registerDto);
}
