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

import com.blu.entity.Type;
import com.blu.service.BlogService;
import com.blu.service.TypeService;
import com.blu.vo.BlogQuery;

@Controller
public class TypeShowController {
	
	@Autowired
	TypeService typeService;
	@Autowired
	BlogService blogService;

	@GetMapping("/types/{id}")
	public String Types(@PageableDefault(size = 5, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,@PathVariable Long id,Model model) {
		
		List<Type> types = typeService.listTypeTop(10000);
		if(id == -1) {
			id = types.get(0).getId();
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTypeId(id);
		model.addAttribute("types", types);
		model.addAttribute("activeTypeId", id);
		model.addAttribute("page", blogService.listPublishedBlog(pageable, blogQuery));
		
		return "types";
	}
}
