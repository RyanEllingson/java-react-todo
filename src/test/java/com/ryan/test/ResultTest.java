package com.ryan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ryan.models.Result;

public class ResultTest {
	
	@Test
	public void isSuccessShouldBeTrue() {
		Result<String> result = new Result<>();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void isSuccessShouldBeFalse() {
		Result<String> result = new Result<>();
		result.addMessage("test message");
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void messagesShouldBeImmutable() {
		Result<String> result = new Result<>();
		result.addMessage("test message");
		List<String> messages1 = result.getMessages();
		messages1.set(0, "modified message");
		List<String> messages2 = result.getMessages();
		assertEquals("modified message", messages1.get(0));
		assertEquals("test message", messages2.get(0));
	}

}
