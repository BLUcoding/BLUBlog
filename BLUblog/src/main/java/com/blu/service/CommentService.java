package com.blu.service;

import java.util.List;

import com.blu.entity.comment;

public interface CommentService {
	
	List<comment> listCommentByBlogId(Long blogId);
	
	comment saveComment(comment comment);
	
}
