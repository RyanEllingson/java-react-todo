package com.ryan.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.data.TodoDatabaseRepository;
import com.ryan.data.TodoRepository;
import com.ryan.models.Todo;
import com.ryan.util.ConnectionFactory;
import com.ryan.util.Environment;

public class TodoDatabaseRepositoryTest {
	private static TodoRepository todoRepo;
	
	@BeforeClass
	public static void setup() {
		ConnectionFactory.setEnvironment(Environment.TEST);
		todoRepo = new TodoDatabaseRepository(ConnectionFactory.getConnection());
	}
	
	@Before
	public void restoreKnownGoodState() {
		ConnectionFactory.restoreKnownGoodState();
	}
	
	@Test
	public void shouldCreateUser() {
		Todo todo = new Todo(0, 1, "walk the cat", true);
		int insertId = todoRepo.createTodo(todo);
		assertEquals(2, insertId);
		todo.setTodoId(insertId);
		Todo createdTodo = todoRepo.getTodoById(insertId);
		assertEquals(todo, createdTodo);
	}
	
	@Test
	public void shouldGetUserById() {
		Todo expected = new Todo(1, 2, "write test for todos", false);
		Todo actual = todoRepo.getTodoById(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotGetNonExistingId() {
		Todo expected = new Todo();
		Todo actual = todoRepo.getTodoById(2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldGetTodosByUser() {
		List<Todo> expected = new ArrayList<>();
		expected.add(new Todo(1, 2, "write test for todos", false));
		List<Todo> actual = todoRepo.getTodosByUser(2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotGetTodosForNonExistingUser() {
		List<Todo> expected = new ArrayList<>();
		List<Todo> actual = todoRepo.getTodosByUser(3);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldUpdateTodo() {
		Todo todo = new Todo(1, 2, "write test for todos", true);
		assertEquals(1, todoRepo.updateTodo(todo));
		Todo updatedTodo = todoRepo.getTodoById(1);
		assertEquals(todo, updatedTodo);
	}
	
	@Test
	public void shouldNotUpdateNonExistingTodo() {
		Todo todo = new Todo(2, 2, "bogus todo", true);
		assertEquals(0, todoRepo.updateTodo(todo));
	}
	
	@Test
	public void shouldDeleteTodo() {
		assertEquals(1, todoRepo.deleteTodoById(1));
		Todo expected = new Todo();
		Todo actual = todoRepo.getTodoById(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldNotDeleteNonExistingTodo() {
		assertEquals(0, todoRepo.deleteTodoById(2));
	}

}
