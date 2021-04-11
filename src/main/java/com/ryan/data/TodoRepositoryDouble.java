package com.ryan.data;

import java.util.ArrayList;
import java.util.List;

import com.ryan.models.Todo;

public class TodoRepositoryDouble implements TodoRepository {
	private Todo testTodo;
	
	public TodoRepositoryDouble() {
		testTodo = new Todo(1, 1, "test todo", true);
	}

	@Override
	public int createTodo(Todo todo) {
		return 2;
	}

	@Override
	public Todo getTodoById(int todoId) {
		Todo todo;
		if (todoId == 1) {
			todo = testTodo;
		} else {
			todo = new Todo();
		}
		return todo;
	}

	@Override
	public List<Todo> getTodosByUser(int userId) {
		List<Todo> todos = new ArrayList<>();
		if (userId == 1) {
			todos.add(testTodo);
		}
		return todos;
	}

	@Override
	public int updateTodo(Todo todo) {
		return 1;
	}

	@Override
	public int deleteTodoById(int todoId) {
		return 1;
	}

}
