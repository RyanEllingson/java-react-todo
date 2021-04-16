package com.ryan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.User;
import com.ryan.service.UserService;
import com.ryan.util.HashGenerator;

public class UserUpdatePasswordTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldUpdatePassword() {
		User user = new User(1, "Test", "Testeroo", "test@test.com", "betterpassword", null);
		Result<User> result = userService.updatePassword(user);
		String[] passwordParams = result.getPayload().getPassword().split("/");
		assertTrue(HashGenerator.comparePasswords(passwordParams[0], "betterpassword", passwordParams[1]));
	}
	
	@Test
	public void shouldNotUpdateNonExistingUser() {
		User user = new User(2, "Test", "Testeroo", "test@test.com", "betterpassword", null);
		Result<User> expected = new Result<>();
		expected.addMessage("userId", "User not found");
		Result<User> actual = userService.updatePassword(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeNull() {
		User user = new User(1, "Test", "Testeroo", "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		Result<User> actual = userService.updatePassword(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeBlank() {
		User user = new User(1, "Test", "Testeroo", "test@test.com", "   ", null);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		Result<User> actual = userService.updatePassword(user);
		assertEquals(expected, actual);
	}

}
