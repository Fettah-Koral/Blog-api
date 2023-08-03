package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service	
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,  ModelMapper mapper){
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		comment.setPost(post);
		
		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId).orElseThrow(
				()->new ResourceNotFoundException("comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return mapToDto(updatedComment);
	}

	@Override
	public List<CommentDto>  getCommentsByPostId(long postId) {
		return commentRepository.findByPostId(postId).stream()
				.map((c) -> mapToDto(c)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));

		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		return mapToDto(comment);
	}

	
	@Override
	public void deleteComment(long postId, long commentId) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));

		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		commentRepository.delete(comment);
	}

	
	private Comment mapToEntity(CommentDto commentDto) {
		
		Comment comment = mapper.map(commentDto, Comment.class);
		
		return comment;
	}
	
	private CommentDto mapToDto(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		return commentDto;
	}



	
}
















