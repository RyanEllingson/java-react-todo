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
	
	private Result<User> validateRegistration(User user) {
		Result<User> result = new Result<>();
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			result.addMessage("email", "Email is required");
		} else {
			User sameEmail = userRepo.getUserByEmail(user.getEmail());
			if (sameEmail.getUserId() != 0) {
				result.addMessage("email", "Email already in use");
			}
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			result.addMessage("password", "Password is required");
		}
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			result.addMessage("firstName", "First name is required");
		}
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			result.addMessage("lastName", "Last name is required");
		}
		return result;
	}
	
	public Result<User> register(User user) {
		Result<User> result = validateRegistration(user);
		if (result.isSuccess()) {
			user.setPassword(HashGenerator.hashPassword(user.getPassword()));
			user.setUserId(userRepo.createUser(user));
			if (user.getUserId() == 0) {
				result.addMessage("email", "There was a problem creating user");
			} else {
				result.setPayload(user);
			}
		}
		return result;
	}
	
	private Result<User> validateLogin(User user) {
		Result<User> result = new Result<>();
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			result.addMessage("email", "Email is required");
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			result.addMessage("password", "Password is required");
		}
		return result;
	}
	
	public Result<User> login(User user) {
		Result<User> result = validateLogin(user);
		if (result.isSuccess()) {
			User requestedUser = userRepo.getUserByEmail(user.getEmail());
			if (requestedUser.getUserId() == 0) {
				result.addMessage("email", "Email not found");
			} else {
				String[] passwordParams = requestedUser.getPassword().split("/");
				if (HashGenerator.comparePasswords(passwordParams[0], user.getPassword(), passwordParams[1])) {
					result.setPayload(requestedUser);
				} else {
					result.addMessage("password", "Incorrect password");
				}
			}
		}
		return result;
	}

}
