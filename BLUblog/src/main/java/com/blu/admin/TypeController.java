package com.blu.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blu.entity.Type;
import com.blu.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	/**
	 * content 存放数据列表
	 * last 是否是最后一页
	 * totalPages 总页数
	 * totalElements 总条数
	 * size 一页总条数
	 * number 当前第几页
	 * first 是否为第一页
	 * sort.direction 排序顺序
	 * sort.property 按照哪个字段排序
	 * sort.ignoreCase
	 * sort.nullHandling
	 * sort.ascending
	 * numberOfElements 当前页的数据有多少条
	 * 
	 */
	@GetMapping("/types")
	public String types(@PageableDefault(size = 8,sort = {"id"},direction= Sort.Direction.DESC)
			Pageable pageable,Model model) {
		Page<Type> page = typeService.listType(pageable);
		model.addAttribute("page",page);
		return "/admin/types";
	}
	
	@GetMapping("types/input")
	public String input(Model model) {
		model.addAttribute("type",new Type());
		return "admin/types-input";
	}
	
	@PostMapping("/types")
	public String postType(@Valid Type type,BindingResult result,RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if(type1 != null) {
			result.rejectValue("name", "nameError","该分类已存在");
			return "admin/types-input";
		}
		
		if(result.hasErrors()) {
			return "admin/types-input";
		}
		Type t = typeService.saveType(type);
		if (t == null) {
			attributes.addFlashAttribute("message", "新增失败");
		} else {
			attributes.addFlashAttribute("message", "新增成功");
		}
		return "redirect:/admin/types";
	}
	
	@GetMapping("/types/{id}/input")
	public String editType(@PathVariable Long id, Model model) {
		model.addAttribute("type", typeService.getType(id));
		return "admin/types-input";
	}
	
	@PostMapping("/types/{id}")
	public String editPostType(@Valid Type type, BindingResult result, @PathVariable Long id,RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if(type1 != null) {
			result.rejectValue("name", "nameError","该分类已存在");
			return "admin/types-input";
		}
		
		if(result.hasErrors()) {
			return "admin/types-input";
		}
		Type t = typeService.updateType(id,type);
		if (t == null) {
			attributes.addFlashAttribute("message", "更新失败");
		} else {
			attributes.addFlashAttribute("message", "更新成功");
		}
		return "redirect:/admin/types";
	}
	
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/types";
	}
	
	
}
