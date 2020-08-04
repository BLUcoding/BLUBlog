package com.blu.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blu.dao.TagRepository;
import com.blu.entity.Tag;
import com.blu.exception.NotFoundException;
import com.blu.service.TagService;


@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Transactional
	@Override
	public Tag getTag(Long id) {
		Tag tag = tagRepository.getOne(id);
		return tag;
	}

	@Transactional
	@Override
	public Page<Tag> listTag(Pageable pageable) {
		Page<Tag> page = tagRepository.findAll(pageable);
		return page;
		
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t = tagRepository.getOne(id);
		if (t == null) {
			throw new NotFoundException("该标签不存在");
		}
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Tag getTagByName(String name) {
		Tag tag = tagRepository.findByName(name);
		return tag;
	}

	@Transactional
	@Override
	public List<Tag> listTag() {
		List<Tag> list = tagRepository.findAll();
		return list;
	}

	@Override
	public List<Tag> listTag(String ids) {
		
		return tagRepository.findAllById(convertToList(ids));
	}
	
	private List<Long> convertToList(String ids) {
		List<Long> list = new ArrayList<>();
		if(!"".equals(ids) && ids != null) {
			String[] idarray = ids.split(",");
			for (int i=0; i<idarray.length;i++) {
				list.add(new Long(idarray[i]));
			}
		}
		return list;
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = Sort.by(Direction.DESC, "blogs.size");
		PageRequest pageRequest = PageRequest.of(0, size, sort);
		List<Tag> list = tagRepository.findTop(pageRequest);
		return list;
	}

	
}
