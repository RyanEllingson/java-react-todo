package com.ryan.data;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ryan.models.Todo;

public class TodoDatabaseRepository implements TodoRepository {
	private Connection conn;
	
	public TodoDatabaseRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public int createTodo(Todo todo) {
		int insertId = 0;
		String sql = "insert into todos (user_id, task, completed) values (?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, todo.getUserId());
			ps.setString(2, todo.getTask());
			ps.setBoolean(3, todo.isCompleted());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				insertId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertId;
	}

	@Override
	public Todo getTodoById(int todoId) {
		Todo todo = new Todo();
		String sql = "select todo_id, user_id, task, completed from todos where todo_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, todoId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				todo.setTodoId(rs.getInt(1));
				todo.setUserId(rs.getInt(2));
				todo.setTask(rs.getString(3));
				todo.setCompleted(rs.getBoolean(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return todo;
	}

	@Override
	public List<Todo> getTodosByUser(int userId) {
		List<Todo> todos = new ArrayList<>();
		String sql = "select todo_id, user_id, task, completed from todos where user_id = ? order by todo_id asc";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				todos.add(new Todo(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getBoolean(4)
					));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return todos;
	}

	@Override
	public int updateTodo(Todo todo) {
		int affectedRows = 0;
		String sql = "update todos set user_id = ?, task = ?, completed = ? where todo_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, todo.getUserId());
			ps.setString(2, todo.getTask());
			ps.setBoolean(3, todo.isCompleted());
			ps.setInt(4, todo.getTodoId());
			affectedRows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Override
	public int deleteTodoById(int todoId) {
		int affectedRows = 0;
		String sql = "delete from todos where todo_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, todoId);
			affectedRows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

}
