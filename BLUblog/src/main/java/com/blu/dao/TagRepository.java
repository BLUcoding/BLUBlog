package com.blu.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blu.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
	
	@Query("select t from Tag t")
	List<Tag> findTop(PageRequest pageRequest);
	
	
}
