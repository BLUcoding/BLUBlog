package com.blu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blu.service.BlogService;

@Controller
public class ArchiveShowController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/archives")
	public String archives(Model model) {
		model.addAttribute("achiveMap", blogService.archiveBlog());
		model.addAttribute("blogCount", blogService.countBlog());
		return "archives";
	}
}
