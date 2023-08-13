package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	private CategoryRepository categoryRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}
	
	

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = listOfPosts.stream().map((p) -> mapToDto(p)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	
	
	@Override
	public PostDto getPostById(long id) {
		
		Post post = postRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		return mapToDto(post);
	}


	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
				()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
				()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		postDto.setId(id);
		
		Post updatedPost = mapToEntity(postDto);
		updatedPost.setCategory(category);
		
		postRepository.save(updatedPost);
		
		return mapToDto(updatedPost);
		
	}
	

	@Override
	public void deletePostById(long id) {
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);
	}


	
	private PostDto mapToDto(Post post) {
		
		PostDto postDto = mapper.map(post, PostDto.class);
		
		return postDto;
	}
	
	
	
	private Post mapToEntity(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		
		return post;
	}



	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		return posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
	}


	
	
	
	
}
