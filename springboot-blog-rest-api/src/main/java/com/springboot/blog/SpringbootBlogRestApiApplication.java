package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot Blog App  Rest APIs",
				description = "Spring boot Blog App  Rest APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "muhammet fettah koral",
						email = "koralfettah@gmail.com",
						url=""
				),
				license = @License(
						name = "Apache 2.0",
						url=""
				)
				
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Blog App Documentation",
				url = "https://github.com/Fettah-Koral/Blog-api/tree/main/springboot-blog-rest-api"
		)
)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	

}
