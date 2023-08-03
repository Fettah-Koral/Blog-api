package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId")long postId,
													@Valid @RequestBody CommentDto commentDto)
	{
		CommentDto createdComment = commentService.createComment(postId, commentDto);
		return new ResponseEntity<> (createdComment, HttpStatus.CREATED);
	}
	
	@GetMapping("posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value="postId") long postId){
		List<CommentDto> comments =commentService.getCommentsByPostId(postId);
		return comments ;
	}
	
	@GetMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentsByPostId(@PathVariable(value="postId") long postId,
														  @PathVariable(value = "id") long commentId){
		
		CommentDto comment = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@PutMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") long postId,
													@PathVariable(value="id") long commentId,
													@Valid @RequestBody CommentDto commentDto)
	{
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<> (updatedComment,HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="postId") long postId,
			  									@PathVariable(value = "id") long commentId) {
		
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successully", HttpStatus.OK);
	}
	
	
}















