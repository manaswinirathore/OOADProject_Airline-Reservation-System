package com.airline.service;

import java.util.List;

import com.airline.entities.User;

public interface UserService {
	User createUser(User user);

	void deleteUserById(String email);

	User getUserById(String email);

	List<User> listofUsers();

	void updateUser(User updateUser);
}
