package com.ryan.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ryan.controllers.TodoController;
import com.ryan.controllers.UserController;

public class PutRequestHelper {
	
	public static void process(HttpServletRequest req, HttpServletResponse res) {
		String uri = req.getRequestURI();
		if (uri.matches("/api/todos")) {
			TodoController.updateTodo(req, res);
		} else if (uri.matches("/api/users/info")) {
			UserController.updateUserInfo(req, res);
		} else if (uri.matches("/api/users/password")) {
			UserController.updatePassword(req, res);
		}
	}
}
