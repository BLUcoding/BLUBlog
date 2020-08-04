package com.blu.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.blu.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog>{
	
	@Query("select b from Blog b where b.recommend = true and b.published = true")
	List<Blog> findTop(PageRequest pageRequest);
	
	@Query("select b from Blog b where b.title like ?1 and b.published = true or b.content like ?1 and b.published = true or b.description like ?1 and b.published = true")
	Page<Blog> findByQuery(String query,Pageable pageable);

	@Transactional
	@Modifying
	@Query("update Blog b set b.views = b.views+1 where b.id = ?1")
	int updateViews(Long id);

	/*
	 * 原始查询语句：
	 * SELECT date_format(b.update_time, '%Y') as year from t_blog b GROUP BY year
	 * ORDER BY year DESC;
	 * 获得的数据格式：
	 * year
	 * 2020
	 * 2019
	 * 2018
	 * 2017
	 */
	@Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc") 
	List<String> findGroupYear();
	
	@Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 ")
	List<Blog> findByYear(String year);
	
	@Query("select b from Blog b where b.published = true")
	Page<Blog> findAllPublishedBlog(Pageable pageable);
	
}
