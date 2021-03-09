package com.ryan.data;

import com.ryan.models.User;

public interface UserRepository {
	
	public int createUser(User user);
	
	public User getUserById(int userId);
	
	public User getUserByEmail(String email);
	
	public int updateUser(User user);
	
	public int deleteUserById(int userId);

}
