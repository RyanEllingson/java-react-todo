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

public class UserGetTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldGetUserByEmail() {
		Result<User> result = userService.getUserByEmail("test@test.com");
		User user = result.getPayload();
		assertEquals(1, user.getUserId());
		assertEquals("Test", user.getFirstName());
		assertEquals("Testeroo", user.getLastName());
		assertEquals("test@test.com", user.getEmail());
		String[] passwordParams = user.getPassword().split("/");
		assertTrue(HashGenerator.comparePasswords(passwordParams[0], "password", passwordParams[1]));
		String[] resetCodeParams = user.getResetCode().split("/");
		assertTrue(HashGenerator.comparePasswords(resetCodeParams[0], "resetCode", resetCodeParams[1]));
	}
	
	@Test
	public void emailShouldNotBeNull() {
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.getUserByEmail(null);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.getUserByEmail("  ");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotFindNonExistingEmail() {
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email not found");
		Result<User> actual = userService.getUserByEmail("bogusemail@test.com");
		assertEquals(expected, actual);
	}

}
