package com.ryan.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.TodoRepositoryDouble;
import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Todo;
import com.ryan.service.TodoService;

public class TodoGetTest {
	private static TodoService todoService;
	
	@BeforeClass
	public static void setup() {
		todoService = new TodoService(new TodoRepositoryDouble(), new UserRepositoryDouble());
	}
	
	@Test
	public void shouldGetTodos() {
		List<Todo> expected = new ArrayList<>();
		expected.add(new Todo(1, 1, "test todo", true));
		List<Todo> actual = todoService.getTodosByUser(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotGetTodosForNonExistingUser() {
		List<Todo> expected = new ArrayList<>();
		List<Todo> actual = todoService.getTodosByUser(2);
		assertEquals(expected, actual);
	}

}
