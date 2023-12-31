package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.JWTAuthResponse;
import com.springboot.blog.dtos.LoginDto;
import com.springboot.blog.dtos.RegisterDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;
	
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value={"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
	
	@PostMapping(value={"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.Register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	 
}
















