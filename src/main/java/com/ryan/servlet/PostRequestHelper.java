package com.ryan.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ryan.controllers.TodoController;
import com.ryan.controllers.UserController;

public class PostRequestHelper {

	public static void process(HttpServletRequest req, HttpServletResponse res) {
		String uri = req.getRequestURI();
		if (uri.matches("/api/register")) {
			UserController.register(req, res);
		} else if (uri.matches("/api/login")) {
			UserController.login(req, res);
		} else if (uri.matches("/api/todos")) {
			TodoController.addTodo(req, res);
		} else if (uri.matches("/api/reset")) {
			UserController.initiateLoginViaEmail(req, res);
		} else if (uri.matches("/api/email_login")) {
			UserController.loginViaEmail(req, res);
		}
	}
}
