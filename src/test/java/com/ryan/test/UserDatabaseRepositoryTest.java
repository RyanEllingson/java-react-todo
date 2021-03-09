package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.UserDatabaseRepository;
import com.ryan.data.UserRepository;
import com.ryan.models.User;
import com.ryan.util.ConnectionFactory;
import com.ryan.util.Environment;

public class UserDatabaseRepositoryTest {
	private static UserRepository userRepo;
	
	@BeforeClass
	public static void setup() {
		ConnectionFactory.setEnvironment(Environment.TEST);
		userRepo = new UserDatabaseRepository(ConnectionFactory.getConnection());
	}
	
	@Before
	public void restoreKnownGoodState() {
		ConnectionFactory.restoreKnownGoodState();
	}
	
	@Test
	public void shouldCreateUser() {
		User user = new User(2, "Tester", "Testeroo", "test2@test.com", "password2");
		int insertId = userRepo.createUser(user);
		assertEquals(2, insertId);
		User createdUser = userRepo.getUserById(insertId);
		assertEquals(user, createdUser);
	}
	
	@Test
	public void shouldGetUserById() {
		User expected = new User(1, "Testy", "Testerson", "test@test.com", "password");
		User actual = userRepo.getUserById(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotGetNonExistingId() {
		User expected = new User();
		User actual = userRepo.getUserById(2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldGetUserByEmail() {
		User expected = new User(1, "Testy", "Testerson", "test@test.com", "password");
		User actual = userRepo.getUserByEmail("test@test.com");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotGetNonExistingEmail() {
		User expected = new User();
		User actual = userRepo.getUserByEmail("bogusemail");
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldUpdateUser() {
		User user = new User(1, "Testus", "Testensen", "testbla@test.com", "betterpassword");
		int affectedRows = userRepo.updateUser(user);
		assertEquals(1, affectedRows);
		User updatedUser = userRepo.getUserById(1);
		assertEquals(user, updatedUser);
	}
	
	@Test
	public void shouldNotUpdateNonExistingUser() {
		User user = new User(2, "Testus", "Testensen", "testbla@test.com", "betterpassword");
		int affectedRows = userRepo.updateUser(user);
		assertEquals(0, affectedRows);
	}
	
	@Test
	public void shouldDeleteUser() {
		int affectedRows = userRepo.deleteUserById(1);
		assertEquals(1, affectedRows);
	}
	
	@Test
	public void shouldNotDeleteNonExistingUser() {
		int affectedRows = userRepo.deleteUserById(2);
		assertEquals(0, affectedRows);
	}

}
