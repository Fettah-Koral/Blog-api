package com.springboot.blog.dtos;

import lombok.Data;

@Data
public class CommentDto {
	
	private Long id;
	private String name;
	private String email;
	private String body;
}
