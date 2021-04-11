package com.ryan.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ryan.controllers.TodoController;

public class DeleteRequestHelper {

	public static void process(HttpServletRequest req, HttpServletResponse res) {
		String uri = req.getRequestURI();
		if (uri.matches("/api/todos/(.*)")) {
			TodoController.deleteTodo(req, res);
		}
	}
}
