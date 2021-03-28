package com.ryan.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TodoController {

	public static void getTodos(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("inside getTodos");
		System.out.println(req.getHeader("Authorization"));
		res.setStatus(200);
	}
}
