package com.teamtreehouse.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.teamtreehouse.model.User;

public interface UserService extends UserDetailsService{
	
	User findByUsername(String username);

}
