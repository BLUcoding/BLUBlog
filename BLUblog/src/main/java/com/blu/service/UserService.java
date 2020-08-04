package com.blu.service;

import com.blu.entity.User;

public interface UserService {
	
	User checkUser(String username,String password);
}
