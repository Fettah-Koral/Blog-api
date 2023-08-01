package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.entity.Comment;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);
	
	List<CommentDto>  getCommentsByPostId(long postId);

	CommentDto getCommentById(long postId, long commentId);
	
	void deleteComment(long postId, long commentId);
	
	
	
}
