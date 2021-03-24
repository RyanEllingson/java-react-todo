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
		User user = new User(0, null, null, "test@test.com", "password");
		Result<User> result = userService.login(user);
		assertEquals(1, result.getPayload().getUserId());
		assertEquals("Test", result.getPayload().getFirstName());
		assertEquals("Testeroo", result.getPayload().getLastName());
		assertEquals("test@test.com", result.getPayload().getEmail());
	}
	
	@Test
	public void emailShouldNotBeNull() {
		User user = new User (0, null, null, null, "password");
		Result<User> expected = new Result<>();
		expected.addMessage("Email is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		User user = new User (0, null, null, "   ", "password");
		Result<User> expected = new Result<>();
		expected.addMessage("Email is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotFindNonExistingEmail() {
		User user = new User (0, null, null, "blah@blah.com", "password");
		Result<User> expected = new Result<>();
		expected.addMessage("Email not found");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeNull() {
		User user = new User (0, null, null, "test@test.com", null);
		Result<User> expected = new Result<>();
		expected.addMessage("Password is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeBlank() {
		User user = new User (0, null, null, "test@test.com", "   ");
		Result<User> expected = new Result<>();
		expected.addMessage("Password is required");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotLoginWithIncorrectPassword() {
		User user = new User (0, null, null, "test@test.com", "blabla");
		Result<User> expected = new Result<>();
		expected.addMessage("Incorrect password");
		Result<User> actual = userService.login(user);
		assertEquals(expected, actual);
	}

}
