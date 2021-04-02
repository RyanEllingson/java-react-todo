package com.ryan.data;

import java.util.List;

import com.ryan.models.Todo;

public class TodoRepositoryDouble implements TodoRepository {

	@Override
	public int createTodo(Todo todo) {
		return 2;
	}

	@Override
	public Todo getTodoById(int todoId) {
		return null;
	}

	@Override
	public List<Todo> getTodosByUser(int userId) {
		return null;
	}

	@Override
	public int updateTodo(Todo todo) {
		return 0;
	}

	@Override
	public int deleteTodoById(int todoId) {
		return 0;
	}

}
