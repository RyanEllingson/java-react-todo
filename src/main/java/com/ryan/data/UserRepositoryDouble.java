package com.ryan.data;

import com.ryan.models.User;
import com.ryan.util.HashGenerator;

public class UserRepositoryDouble implements UserRepository {
	private User repoUser = new User(1, "Test", "Testeroo", "test@test.com", HashGenerator.hashPassword("password"), HashGenerator.hashPassword("resetCode"));

	@Override
	public int createUser(User user) {
		return 2;
	}

	@Override
	public User getUserById(int userId) {
		User user = new User();
		if (userId == 1) {
			user = repoUser;
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = new User();
		if (email.equals("test@test.com")) {
			user = repoUser;
		} else if (email.equals("existingEmail@test.com")) {
			user.setUserId(3);
		}
		return user;
	}

	@Override
	public int updateUser(User user) {
		int affectedRows = 0;
		if (user.getUserId() == 1) {
			affectedRows = 1;
		}
		return affectedRows;
	}

	@Override
	public int deleteUserById(int userId) {
		return 0;
	}

}
