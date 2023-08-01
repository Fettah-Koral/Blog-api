package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	
	private PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	

	@Override
	public List<PostDto> getAllPosts() {
		
		List<Post> posts = postRepository.findAll();
		return posts.stream().map((p) -> mapToDto(p)).collect(Collectors.toList());
	}
	
	
	
	@Override
	public PostDto getPostById(long id) {
		
		Post post = postRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		return mapToDto(post);
	}


	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Post post = mapToEntity(postDto);
		
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = mapToDto(newPost);
		
		return postResponse;
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		postDto.setId(id);
		
		Post updatedPost =postRepository.save(mapToEntity(postDto));
		
		return mapToDto(updatedPost);
		
	}
	

	@Override
	public void deletePostById(long id) {
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);
	}


	
	private PostDto mapToDto(Post post) {
		
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription());
		
		return postDto;
	}
	
	
	
	private Post mapToEntity(PostDto postDto) {
		
		Post post = new Post();
		post.setId(postDto.getId());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		return post;
	}


	
	
	
	
}
