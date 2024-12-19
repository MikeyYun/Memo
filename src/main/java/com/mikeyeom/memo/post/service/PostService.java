package com.mikeyeom.memo.post.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mikeyeom.memo.commom.FileManager;
import com.mikeyeom.memo.post.domain.Post;
import com.mikeyeom.memo.post.repository.PostRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public boolean addPost(
			  int userId
			, String title
			, String contents
			, MultipartFile file) {
		
		String imagePath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
				.userId(userId)
				.title(title)
				.contents(contents)
				.imagePath(imagePath)
				.build();
		
		try {
			Post result = postRepository.save(post);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public List<Post> getPostList(int userId) {
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		return optionalPost.orElse(null);
	}
	
	Map<String, String> resultMap = new HashMap<>();
	
	public boolean updatePost(int id, String title, String contents) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			post.toBuilder().title(title).contents(contents).build();
			
			try{
				postRepository.save(post);
				return true;
			} catch(Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean deletePost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			FileManager.removeFile(post.getImagePath());
			
			postRepository.delete(post);
			
			return true;
		
		} else {
			return false;
		}
	}
}
