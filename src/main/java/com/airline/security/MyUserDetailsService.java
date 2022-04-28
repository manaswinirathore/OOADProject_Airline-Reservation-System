package com.airline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airline.entities.User;
import com.airline.service.UserService;
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userService.getUserById(username);
		if(user==null) {
			throw new UsernameNotFoundException("UserNotFound");
		}
		return new UserPrincipal(user);
	}

	
}
