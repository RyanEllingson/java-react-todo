package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.User;
import com.ryan.service.UserService;

public class UserLoginTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldLoginUser() {
		User user = new User(0, null, null, "test@test.com", "password", null);
		Result<User> result = userService.login(user);
		assertEquals(1, result.getPayload().getUserId());
		assertEquals("Test", result.getPayload().getFirstName());
		assertEquals("Testeroo", result.getPayload().getLastName());
		assertEquals("test@test.com", result.getPayload().getEmail());
	}
	
	@Test
	public void emailShouldNotBeNull() {
		User user = new User (0, null, null, null, "password", null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		User user = new User (0, null, null, "   ", "password", null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotFindNonExistingEmail() {
		User user = new User (0, null, null, "blah@blah.com", "password", null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email not found");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeNull() {
		User user = new User (0, null, null, "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeBlank() {
		User user = new User (0, null, null, "test@test.com", "   ", null);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotLoginWithIncorrectPassword() {
		User user = new User (0, null, null, "test@test.com", "blabla", null);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Incorrect password");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}

}
