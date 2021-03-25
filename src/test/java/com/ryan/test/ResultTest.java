package com.ryan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

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
		result.addMessage("test", "test message");
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void messagesShouldBeImmutable() {
		Result<String> result = new Result<>();
		result.addMessage("test", "test message");
		Map<String, String> messages1 = result.getMessages();
		messages1.put("test", "modified message");
		Map<String, String> messages2 = result.getMessages();
		assertEquals("modified message", messages1.get("test"));
		assertEquals("test message", messages2.get("test"));
	}

}
