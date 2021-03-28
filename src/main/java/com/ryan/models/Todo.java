package com.ryan.models;

public class Todo {
	private int todoId;
	private int userId;
	private String task;
	private boolean completed;
	
	public Todo() {
		super();
	}

	public Todo(int todoId, int userId, String task, boolean completed) {
		super();
		this.todoId = todoId;
		this.userId = userId;
		this.task = task;
		this.completed = completed;
	}

	public int getTodoId() {
		return todoId;
	}

	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result + todoId;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (completed != other.completed)
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		if (todoId != other.todoId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", userId=" + userId + ", task=" + task + ", completed=" + completed + "]";
	}
	

}
