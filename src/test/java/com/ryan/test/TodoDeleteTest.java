package com.ryan.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.TodoRepositoryDouble;
import com.ryan.data.UserRepositoryDouble;
import com.ryan.models.Result;
import com.ryan.models.Todo;
import com.ryan.service.TodoService;

public class TodoDeleteTest {
	private static TodoService todoService;
	
	@BeforeClass
	public static void setup() {
		todoService = new TodoService(new TodoRepositoryDouble(), new UserRepositoryDouble());
	}
	
	@Test
	public void shouldDeleteTodo() {
		Result<Todo> expected = new Result<>();
		Todo todo = new Todo(1, 1, "test todo", true);
		expected.setPayload(todo);
		Result<Todo> actual = todoService.deleteTodo(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotDeleteNonExistingTodo() {
		Result<Todo> expected = new Result<>();
		expected.addMessage("todoId", "Todo not found");
		Result<Todo> actual = todoService.deleteTodo(2);
		assertEquals(expected, actual);
	}

}
