package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.User;
import com.ryan.service.UserService;

public class UserUpdateInfoTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldUpdateUserInfo() {
		User user = new User(1, "Testus", "Testensen", "test2@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.setPayload(user);
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotUpdateNonExistingUser() {
		User user = new User(2, "Testus", "Testensen", "test2@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("userId", "User not found");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeNull() {
		User user = new User(1, "Testus", "Testensen", null, null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		User user = new User(1, "Testus", "Testensen", "   ", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotUpdateToExistingEmail() {
		User user = new User(1, "Testus", "Testensen", "existingEmail@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email already in use");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void firstNameShouldNotBeNull() {
		User user = new User(1, null, "Testensen", "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("firstName", "First name is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void firstNameShouldNotBeBlank() {
		User user = new User(1, "   ", "Testensen", "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("firstName", "First name is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void lastNameShouldNotBeNull() {
		User user = new User(1, "Testus", null, "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("lastName", "Last name is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void lastNameShouldNotBeBlank() {
		User user = new User(1, "Testus", "   ", "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("lastName", "Last name is required");
		Result<User> actual = userService.updateInfo(user);
		assertEquals(expected, actual);
	}

}
