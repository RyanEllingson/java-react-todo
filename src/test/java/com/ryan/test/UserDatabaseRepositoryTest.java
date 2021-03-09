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
	public void shouldGetUserById() {
		User expected = new User(1, "Testy", "Testerson", "test@test.com", "password");
		User actual = userRepo.getUserById(1);
		assertEquals(expected, actual);
	}

}
