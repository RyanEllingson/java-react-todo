package com.ryan.data;

import com.ryan.models.User;
import com.ryan.util.HashGenerator;

public class UserRepositoryDouble implements UserRepository {

	@Override
	public int createUser(User user) {
		return 2;
	}

	@Override
	public User getUserById(int userId) {
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = new User();
		if (email.equals("test@test.com")) {
			user.setUserId(1);
			user.setFirstName("Test");
			user.setLastName("Testeroo");
			user.setEmail("test@test.com");
			user.setPassword(HashGenerator.hashPassword("password"));
		}
		return user;
	}

	@Override
	public int updateUser(User user) {
		return 0;
	}

	@Override
	public int deleteUserById(int userId) {
		return 0;
	}

}
