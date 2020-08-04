package com.blu.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blu.entity.comment;

public interface CommentRepository extends JpaRepository<comment, Long> {
	
	List<comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
