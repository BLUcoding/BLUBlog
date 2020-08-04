package com.blu.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blu.dao.BlogRepository;
import com.blu.entity.Blog;
import com.blu.entity.Type;
import com.blu.exception.NotFoundException;
import com.blu.service.BlogService;
import com.blu.util.MarkdownUtils;
import com.blu.util.MyBeanUtils;
import com.blu.vo.BlogQuery;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Blog getBlog(Long id) {
		Blog blog = blogRepository.getOne(id);
		return blog;
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cB) {
				List<Predicate> predicateList = new ArrayList<>();
				if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicateList.add(cB.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
				}
				if(blog.getTypeId()!= null) {
					predicateList.add(cB.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
				}
				if(blog.isRecommend()) {
					predicateList.add(cB.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		},pageable);
	}
	
	@Override
	public Page<Blog> listPublishedBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cB) {
				List<Predicate> predicateList = new ArrayList<>();
				if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicateList.add(cB.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
				}
				if(blog.getTypeId()!= null) {
					predicateList.add(cB.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
				}
				if(blog.isRecommend()) {
					predicateList.add(cB.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				predicateList.add(cB.equal(root.<Boolean>get("published"), true));
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		},pageable);
	}

	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if(blog.getId() == null) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(blog.getCreateTime());
			blog.setViews(0);
			return blogRepository.save(blog);
		} else {
			blog.setUpdateTime(new Date());
			return blogRepository.save(blog);
		}
		
	}

	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.getOne(id);
		if(b==null) {
			throw new NotFoundException("博客不存在");
		}
		BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogRepository.save(b);
	}

	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}
	
	@Override
	public Page<Blog> listPublishedBlog(Pageable pageable) {
		return blogRepository.findAllPublishedBlog(pageable);
	}

	@Override
	public List<Blog> listPublishedBlogTop(Integer size) {
		Sort sort = Sort.by(Direction.DESC, "updateTime");
		PageRequest pageRequest = PageRequest.of(0, size, sort);
		List<Blog> list = blogRepository.findTop(pageRequest);
		return list;
	}

	@Override
	public Page<Blog> listBlog(String query,Pageable pageable) {
		Page<Blog> page = blogRepository.findByQuery(query, pageable);
		return page;
	}

	@Transactional
	@Override
	public Blog getAndConvertBlog(Long id) {
		Blog blog = blogRepository.getOne(id);
		if(blog == null) {
			throw new NotFoundException("该博客不存在");
		}
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		blogRepository.updateViews(id);
		return b;
	}

	@Override
	public Page<Blog> listBlogsByTagId(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cB) {
				Join join = root.join("tags");
				return cB.equal(join.get("id"), tagId);
			}
			
		},pageable);
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findGroupYear();
		Map<String,List<Blog>> map = new HashMap<>();
		for(String year : years) {
			map.put(year, blogRepository.findByYear(year));
		}
		return map;
	}

	@Override
	public Long countBlog() {
		return blogRepository.count();
	}

}
