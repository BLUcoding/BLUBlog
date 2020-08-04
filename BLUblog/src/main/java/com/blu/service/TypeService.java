package com.blu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blu.entity.Type;

public interface TypeService {
	
	Type saveType(Type type);
	
	Type getType(Long id);
	
	List<Type> listType();
	
	List<Type> listTypeTop(Integer size);
	
	Page<Type> listType(Pageable pageable);
	
	Type updateType(Long id, Type type);
	
	void deleteType(Long id);
	
	Type getTypeByName(String name);
}
