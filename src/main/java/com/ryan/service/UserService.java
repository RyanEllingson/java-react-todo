package com.ryan.service;

import com.ryan.data.UserRepository;
import com.ryan.models.Result;
import com.ryan.models.User;
import com.ryan.util.HashGenerator;

public class UserService {
	private UserRepository userRepo;
	
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	public Result<User> validateRegistration(User user) {
		Result<User> result = new Result<>();
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			result.addMessage("Email is required");
		} else {
			User sameEmail = userRepo.getUserByEmail(user.getEmail());
			if (sameEmail.getUserId() != 0) {
				result.addMessage("Email already in use");
			}
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			result.addMessage("Password is required");
		}
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			result.addMessage("First name is required");
		}
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			result.addMessage("Last name is required");
		}
		return result;
	}
	
	public Result<User> register(User user) {
		Result<User> result = validateRegistration(user);
		if (result.isSuccess()) {
			user.setPassword(HashGenerator.hashPassword(user.getPassword()));
			user.setUserId(userRepo.createUser(user));
			if (user.getUserId() == 0) {
				result.addMessage("There was a problem creating user");
			} else {
				result.setPayload(user);
			}
		}
		return result;
	}

}
