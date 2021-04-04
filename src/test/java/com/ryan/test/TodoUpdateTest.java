package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.TodoRepositoryDouble;
import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.Todo;
import com.ryan.service.TodoService;

public class TodoUpdateTest {
	private static TodoService todoService;
	
	@BeforeClass
	public static void setup() {
		todoService = new TodoService(new TodoRepositoryDouble(), new UserRepositoryDouble());
	}
	
	@Test
	public void shouldUpdateTodo() {
		Todo todo = new Todo(1, 1, "blabla", false);
		Result<Todo> expected = new Result<>();
		expected.setPayload(todo);
		Result<Todo> actual = todoService.updateTodo(todo);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldRequireTodoId() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("todo", "Todo ID is required");
		Result<Todo> actual = todoService.updateTodo(new Todo(0, 1, "test todo", true));
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotSetNonExistingTodoId() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("todo", "Todo not found");
		Result<Todo> actual = todoService.updateTodo(new Todo(2, 1, "do something", false));
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldRequireUserId() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("user", "User is required");
		Result<Todo> actual = todoService.updateTodo(new Todo(1, 0, "do pushups", true));
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotSetNonExistingUserId() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("user", "User not found");
		Result<Todo> actual = todoService.updateTodo(new Todo(1, 2, "chop firewood", false));
		assertEquals(expected, actual);
	}
	
	@Test
	public void taskShouldNotBeNull() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("task", "Task is required");
		Result<Todo> actual = todoService.updateTodo(new Todo(1, 1, null, false));
		assertEquals(expected, actual);
	}
	
	@Test
	public void taskShouldNotBeBlank() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("task", "Task is required");
		Result<Todo> actual = todoService.updateTodo(new Todo(1, 1, "  ", true));
		assertEquals(expected, actual);
	}

}
