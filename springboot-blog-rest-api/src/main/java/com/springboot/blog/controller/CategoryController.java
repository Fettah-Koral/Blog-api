package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.CategoryDto;
import com.springboot.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto savedCategoryDto = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id){
		CategoryDto categoryDto = categoryService.getCategory(id);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtos = categoryService.getAllCategories();
		return ResponseEntity.ok(categoryDtos);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
															 @PathVariable("id") Long categoryId){
		
		CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategoryDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully!.");
	}
	
	 	
}















