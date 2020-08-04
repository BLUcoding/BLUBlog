package com.blu.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blu.entity.Tag;
import com.blu.service.BlogService;
import com.blu.service.TagService;

@Controller
public class TagShowController {

	@Autowired
	TagService tagService;
	@Autowired
	BlogService blogService;
	
	@GetMapping("/tags/{id}")
	public String Tags(@PageableDefault(size = 5, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,@PathVariable Long id,Model model) {
		
		List<Tag> tags = tagService.listTagTop(10000);
		if(id == -1) {
			id = tags.get(0).getId();
		}
		model.addAttribute("tags", tags);
		model.addAttribute("page", blogService.listBlogsByTagId(id, pageable));
		model.addAttribute("activeTagId", id);
		return "tags";
	}
	
}
