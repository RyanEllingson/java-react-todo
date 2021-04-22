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

public class UserLoginViaEmailTest {
	private static UserService userService;
	
	@BeforeClass
	public static void setup() {
		userService = new UserService(new UserRepositoryDouble());
	}
	
	@Test
	public void shouldLoginUserViaEmail() {
		User user = new User(0, null, null, "test@test.com", null, "resetCode");
		Result<User> result = userService.loginViaEmail(user);
		User loggedInUser = result.getPayload();
		assertEquals(1, loggedInUser.getUserId());
		assertEquals("Test", loggedInUser.getFirstName());
		assertEquals("Testeroo", loggedInUser.getLastName());
		assertEquals("test@test.com", loggedInUser.getEmail());
	}
	
	@Test
	public void emailShouldNotBeNull() {
		User user = new User(0, null, null, null, null, "resetCode");
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void emailShouldNotBeBlank() {
		User user = new User(0, null, null, "   ", null, "resetCode");
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email is required");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotFindNonExistingEmail() {
		User user = new User(0, null, null, "bla@bla.com", null, "resetCode");
		Result<User> expected = new Result<>();
		expected.addMessage("email", "Email not found");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void resetCodeShouldNotBeNull() {
		User user = new User(0, null, null, "test@test.com", null, null);
		Result<User> expected = new Result<>();
		expected.addMessage("resetCode", "Reset code is required");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void resetCodeShouldNotBeBlank() {
		User user = new User(0, null, null, "test@test.com", null, "   ");
		Result<User> expected = new Result<>();
		expected.addMessage("resetCode", "Reset code is required");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotLoginWithIncorrectResetCode() {
		User user = new User(0, null, null, "test@test.com", null, "blabla");
		Result<User> expected = new Result<>();
		expected.addMessage("resetCode",  "Incorrect reset code");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotLoginUserWithNoResetCode() {
		User user = new User(0, null, null, "existingEmail@test.com", null, "resetCode");
		Result<User> expected = new Result<>();
		expected.addMessage("resetCode",  "Incorrect reset code");
		Result<User> actual = userService.loginViaEmail(user);
		assertEquals(expected, actual);
	}
	
}
