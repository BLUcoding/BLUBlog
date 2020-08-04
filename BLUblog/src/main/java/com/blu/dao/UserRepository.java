package com.blu.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blu.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByUsernameAndPassword(String username,String password);
}
