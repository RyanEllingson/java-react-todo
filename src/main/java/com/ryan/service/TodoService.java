package com.ryan.service;

import java.util.List;

import com.ryan.data.TodoRepository;
import com.ryan.data.UserRepository;
import com.ryan.models.Result;
import com.ryan.models.Todo;
import com.ryan.models.User;

public class TodoService {
	private TodoRepository todoRepo;
	private UserRepository userRepo;
	
	public TodoService(TodoRepository todoRepo, UserRepository userRepo) {
		this.todoRepo = todoRepo;
		this.userRepo = userRepo;
	}
	
	private Result<Todo> validateCreate(Todo todo) {
		Result<Todo> result = new Result<>();
		if (todo.getUserId() == 0) {
			result.addMessage("user", "User is required");
		} else {
			User user = userRepo.getUserById(todo.getUserId());
			if (user.getUserId() == 0) {
				result.addMessage("user", "User not found");
			}
		}
		if (todo.getTask() == null || todo.getTask().isBlank()) {
			result.addMessage("task", "Task is required");
		}
		return result;
	}
	
	public Result<Todo> createTodo(Todo todo) {
		Result<Todo> result = validateCreate(todo);
		if (result.isSuccess()) {
			todo.setTodoId(todoRepo.createTodo(todo));
			if (todo.getTodoId() == 0) {
				result.addMessage("task", "There was a problem creating todo");
			} else {
				result.setPayload(todo);
			}
		}
		return result;
	}
	
	public List<Todo> getTodosByUser(int userId) {
		return todoRepo.getTodosByUser(userId);
	}
	
	private Result<Todo> validateUpdate(Todo todo) {
		Result<Todo> result = new Result<>();
		if (todo.getTodoId() == 0) {
			result.addMessage("todo", "Todo ID is required");
		} else {
			Todo desiredTodo = todoRepo.getTodoById(todo.getTodoId());
			if (desiredTodo.getTodoId() == 0) {
				result.addMessage("todo", "Todo not found");
			}
		}
		if (todo.getUserId() == 0) {
			result.addMessage("user", "User is required");
		} else {
			User user = userRepo.getUserById(todo.getUserId());
			if (user.getUserId() == 0) {
				result.addMessage("user", "User not found");
			}
		}
		if (todo.getTask() == null || todo.getTask().isBlank()) {
			result.addMessage("task", "Task is required");
		}
		return result;
	}
	
	public Result<Todo> updateTodo(Todo todo) {
		Result<Todo> result = validateUpdate(todo);
		if (result.isSuccess()) {
			int affectedRows = todoRepo.updateTodo(todo);
			if (affectedRows == 0) {
				result.addMessage("todo", "There was a problem updating todo");
			} else {
				result.setPayload(todo);
			}
		}
		return result;
	}
}
