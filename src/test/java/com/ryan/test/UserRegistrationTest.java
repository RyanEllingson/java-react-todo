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

public class UserRegistrationTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldRegisterUser() {
		User user = new User(0, "testy", "tester", "test2@test.com", "password", "resetCode");
		Result<User> result = userService.register(user);
		String[] passwordParams = result.getPayload().getPassword().split("/");
		assertEquals(2, result.getPayload().getUserId());
		assertEquals("testy", result.getPayload().getFirstName());
		assertEquals("tester", result.getPayload().getLastName());
		assertEquals("test2@test.com", result.getPayload().getEmail());
		assertTrue(HashGenerator.comparePasswords(passwordParams[0], "password", passwordParams[1]));
		assertEquals("resetCode", result.getPayload().getResetCode());
	}
	
	@Test
	public void firstNameShouldNotBeNull() {
		User user = new User(0, null, "blah", "blah@blah.com", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("firstName", "First name is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void firstNameShouldNotBeBlank() {
		User user = new User(0, "   ", "blah", "blah@blah.com", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("firstName", "First name is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void lastNameShouldNotBeNull() {
		User user = new User(0, "blah", null, "blah@blah.com", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("lastName", "Last name is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void lastNameShouldNotBeBlank() {
		User user = new User(0, "blah", "   ", "blah@blah.com", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("lastName", "Last name is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeNull() {
		User user = new User(0, "blah", "blah", null, "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		User user = new User(0, "blah", "blah", "   ", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotAllowDuplicateEmail() {
		User user = new User(0, "blah", "blah", "test@test.com", "blabla", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email already in use");
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeNull() {
		User user = new User(0, "blah", "blah", "blah@blah.com", null, null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		assertEquals(expected, actual);
	}
	
	@Test
	public void passwordShouldNotBeBlank() {
		User user = new User(0, "blah", "blah", "blah@blah.com", "   ", null);
		Result<User> actual = userService.register(user);
		Result<User> expected = new Result<>();
		expected.addMessage("password", "Password is required");
		assertEquals(expected, actual);
	}

}
