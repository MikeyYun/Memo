package com.mikeyeom.memo.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikeyeom.memo.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public List<Post> findByUserIdOrderByIdDesc(int userId);
}
