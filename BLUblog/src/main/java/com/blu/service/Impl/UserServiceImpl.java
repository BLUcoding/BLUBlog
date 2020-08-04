package com.blu.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blu.dao.UserRepository;
import com.blu.entity.User;
import com.blu.service.UserService;
import com.blu.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User checkUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}

}
