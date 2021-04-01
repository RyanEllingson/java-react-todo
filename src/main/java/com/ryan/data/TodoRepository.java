package com.ryan.data;

import java.util.List;

import com.ryan.models.Todo;

public interface TodoRepository {

	public int createTodo(Todo todo);
	
	public Todo getTodoById(int todoId);
	
	public List<Todo> getTodosByUser(int userId);
	
	public int updateTodo(Todo todo);
	
	public int deleteTodoById(int todoId);
	
}
