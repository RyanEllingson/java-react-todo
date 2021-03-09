package com.ryan.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ryan.models.User;

public class UserDatabaseRepository implements UserRepository {
	private Connection conn;
	
	public UserDatabaseRepository(Connection conn) {
		this.conn = conn;
	}

	public int createUser(User user) {
		return 0;
	}

	public User getUserById(int userId) {
		User user = new User();
		String sql = "select user_id, first_name, last_name, email, password from users where user_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public User getUserByEmail(String email) {
		return null;
	}

	public int updateUser(User user) {
		return 0;
	}

	public int deleteUserById(int userId) {
		return 0;
	}

}
