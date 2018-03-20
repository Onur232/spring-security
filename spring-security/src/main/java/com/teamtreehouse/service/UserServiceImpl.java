package com.teamtreehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teamtreehouse.dao.UserDao;
import com.teamtreehouse.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	//Bizim userservice'e yazdığımız metot
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	//UserDetailsService'ten gelen java'dan.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 //Load user from the database (throw exception if not found)
		User user=userDao.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		//Return user object
		return user;
	}


}
