package com.ryan.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ryan.controllers.TodoController;

public class GetRequestHelper {

	public static void process(HttpServletRequest req, HttpServletResponse res) {
		String uri = req.getRequestURI();
		if (uri.matches("/api/todos")) {
			TodoController.getTodos(req, res);
		} else if (uri.matches("/login") || uri.matches("/register") || uri.matches("/update/(.*)") || uri.matches("/reset")) {
			try {
				RequestDispatcher redir = req.getRequestDispatcher("/index.html");
				redir.forward(req, res);
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}
	}
}
