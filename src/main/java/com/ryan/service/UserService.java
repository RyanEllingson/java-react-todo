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
		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			result.addMessage("email", "Email is required");
		} else {
			User sameEmail = userRepo.getUserByEmail(user.getEmail());
			if (sameEmail.getUserId() != 0) {
				result.addMessage("email", "Email already in use");
			}
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			result.addMessage("password", "Password is required");
		}
		if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
			result.addMessage("firstName", "First name is required");
		}
		if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
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
		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			result.addMessage("email", "Email is required");
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
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
	
	private Result<User> validateUpdate(User user) {
		Result<User> result = new Result<>();
		if (userRepo.getUserById(user.getUserId()).getUserId() == 0) {
			result.addMessage("userId", "User not found");
		} else {
			if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
				result.addMessage("password", "Password is required");
			}
			if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
				result.addMessage("email", "Email is required");
			} else {
				User sameEmail = userRepo.getUserByEmail(user.getEmail());
				if (sameEmail.getUserId() != 0 && sameEmail.getUserId() != user.getUserId()) {
					result.addMessage("email", "Email already in use");
				}
			}
			if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
				result.addMessage("firstName", "First name is required");
			}
			if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
				result.addMessage("lastName", "Last name is required");
			}
		}
		return result;
	}
	
	public Result<User> updateInfo(User user) {
		user.setPassword(userRepo.getUserById(user.getUserId()).getPassword());
		Result<User> result = validateUpdate(user);
		if (result.isSuccess()) {
			if (userRepo.updateUser(user) < 1) {
				result.addMessage("userId", "Unable to update user information");
			} else {
				result.setPayload(user);
			}
		}
		return result;
	}
	
	public Result<User> updatePassword(User user) {
		User updatedUser = userRepo.getUserById(user.getUserId());
		updatedUser.setPassword(user.getPassword());
		Result<User> result = validateUpdate(updatedUser);
		if (result.isSuccess()) {
			updatedUser.setPassword(HashGenerator.hashPassword(user.getPassword()));
			if (userRepo.updateUser(updatedUser) < 1) {
				result.addMessage("userId", "Unable to update password");
			} else {
				result.setPayload(updatedUser);
			}
		}
		return result;
	}
	
	public Result<User> getUserByEmail(String email) {
		Result<User> result = new Result<>();
		if (email == null || email.trim().isEmpty()) {
			result.addMessage("email", "Email is required");
		} else {
			User user = userRepo.getUserByEmail(email);
			if (user.getUserId() == 0) {
				result.addMessage("email", "Email not found");
			} else {
				result.setPayload(user);
			}
		}
		return result;
	}

}
