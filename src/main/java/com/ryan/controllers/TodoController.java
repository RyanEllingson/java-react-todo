package com.ryan.controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryan.data.TodoDatabaseRepository;
import com.ryan.data.UserDatabaseRepository;
import com.ryan.service.TodoService;
import com.ryan.util.ConnectionFactory;
import com.ryan.util.JWTBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class TodoController {

	public static void getTodos(HttpServletRequest req, HttpServletResponse res) {
		Connection conn = ConnectionFactory.getConnection();
		TodoService todoService = new TodoService(new TodoDatabaseRepository(conn), new UserDatabaseRepository(conn));
		try {
			ObjectMapper om = new ObjectMapper();
			Jws<Claims> jws = JWTBuilder.parseJWS(req.getHeader("Authorization"));
			if (jws == null) {
				res.setStatus(401);
			} else {
				int userId = (int) jws.getBody().get("userId");
				res.setStatus(200);
				res.getWriter().write(om.writeValueAsString(todoService.getTodosByUser(userId)));
			}
		} catch (IOException e) {
			res.setStatus(400);
		}
	}
}
