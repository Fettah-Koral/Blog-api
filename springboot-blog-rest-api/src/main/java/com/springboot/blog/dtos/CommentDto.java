package com.springboot.blog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	
	private Long id;
	
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@Email
	@NotEmpty(message = "Email should not be null or empty")
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Comment body must be minumum 10 characters")
	private String body;
}
