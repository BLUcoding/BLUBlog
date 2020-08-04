package com.blu.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blu.entity.Blog;
import com.blu.service.BlogService;
import com.blu.service.TagService;
import com.blu.service.TypeService;

@Controller
public class indexController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/")
	public String index(@PageableDefault(size = 6, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,Model model) {
		
		model.addAttribute("page", blogService.listPublishedBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(7));
		model.addAttribute("recommendBlogs", blogService.listPublishedBlogTop(8));
		return "index";
	}

	@PostMapping("/search")
	public String Serach(@PageableDefault(size = 6, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, 
				Model model) {
		String que = "%"+query+"%";
		Page<Blog> page = blogService.listBlog(que,pageable);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		
		return "search";
	}
	
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id, Model model) {
		Blog blog = blogService.getAndConvertBlog(id);
		blog.setViews(blog.getViews()+1);
		model.addAttribute("blog", blog);
		return "blog";
	}
	
	@GetMapping("/login")
	public String login() {
		return "admin/login";
	}
	
	@GetMapping("/admin/index")
	public String adminindex() {
		return "/admin/index";
	}
	
}
