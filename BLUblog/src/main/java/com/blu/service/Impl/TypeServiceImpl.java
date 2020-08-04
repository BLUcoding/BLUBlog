package com.blu.service.Impl;

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

import com.blu.dao.TypeRepository;
import com.blu.entity.Type;
import com.blu.exception.NotFoundException;
import com.blu.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeRepository typeRepository;

	@Transactional
	@Override
	public Type saveType(Type type) {
		return typeRepository.save(type);
	}

	@Transactional
	@Override
	public Type getType(Long id) {
		Type type = typeRepository.getOne(id);
		return type;
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		Page<Type> page = typeRepository.findAll(pageable);
		return page;
	}

	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Type t = typeRepository.getOne(id);
		if (t == null) {
			throw new NotFoundException("该类型不存在");
		}
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
		
	}

	@Override
	public Type getTypeByName(String name) {
		Type type = typeRepository.findByName(name);
		return type;
	}

	@Override
	public List<Type> listType() {
		List<Type> list = typeRepository.findAll();
		return list;
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort = Sort.by(Direction.DESC, "blogs.size");
		PageRequest pageRequest = PageRequest.of(0, size, sort);
		List<Type> list = typeRepository.findTop(pageRequest);
		return list;
	}

}
