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
	private static TodoService todoService;
	
	@BeforeClass
	public static void setup() {
		todoService = new TodoService(new TodoRepositoryDouble(), new UserRepositoryDouble());
	}
	
	@Test
	public void shouldCreateTodo() {
		Todo todo = new Todo(0, 1, "feed the fish", false);
		Result<Todo> expected = new Result<>();
		expected.setPayload(todo);
		Result<Todo> actual = todoService.createTodo(todo);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldRequireUserId() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("user", "User is required");
		Result<Todo> actual = todoService.createTodo(new Todo(0, 0, "milk the chickens", true));
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotCreateTodoForNonExistingUser() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("user", "User not found");
		Result<Todo> actual = todoService.createTodo(new Todo(0, 2, "shave the dog", false));
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldRequireTask() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("task", "Task is required");
		Result<Todo> actual = todoService.createTodo(new Todo(0, 1, null, false));
		assertEquals(expected, actual);
	}

}
