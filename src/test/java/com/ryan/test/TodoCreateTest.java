package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.TodoRepositoryDouble;
import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.Todo;
import com.ryan.service.TodoService;

public class TodoCreateTest {
	private static TodoService service;
	
	@BeforeClass
	public static void setup() {
		service = new TodoService(new TodoRepositoryDouble(), new UserRepositoryDouble());
	}
	
	@Test
	public void shouldCreateTodo() {
		Todo todo = new Todo(0, 1, "feed the fish", false);
		Result<Todo> expected = new Result<>();
		expected.setPayload(todo);
		Result<Todo> actual = service.createTodo(todo);
		assertEquals(expected, actual);
	}

}
