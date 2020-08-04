package com.blu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blu.entity.Tag;

public interface TagService {
	
	Tag saveTag(Tag tag);
	
	Tag getTag(Long id);
	
	List<Tag> listTag();
	
	List<Tag> listTagTop(Integer size);
	
	List<Tag> listTag(String ids);
	
	Page<Tag> listTag(Pageable pageable);
	
	Tag updateTag(Long id,Tag tag);
	
	void deleteTag(Long id);
	
	Tag getTagByName(String name);
	
}
