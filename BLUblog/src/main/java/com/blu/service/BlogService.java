package com.blu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blu.entity.Blog;
import com.blu.vo.BlogQuery;

public interface BlogService {
	Blog getBlog(Long id);
	Blog getAndConvertBlog(Long id);
	List<Blog> listPublishedBlogTop(Integer size);
	Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
	Page<Blog> listBlog(String query,Pageable pageable);
	Page<Blog> listBlog(Pageable pageable);
	Page<Blog> listBlogsByTagId(Long tagId,Pageable pageable);
	Map<String, List<Blog>> archiveBlog();
	Long countBlog();
	Blog saveBlog(Blog blog);
	Blog updateBlog(Long id,Blog blog);
	void deleteBlog(Long id);
	Page<Blog> listPublishedBlog(Pageable pageable);
	Page<Blog> listPublishedBlog(Pageable pageable, BlogQuery blog);
	
	
}
