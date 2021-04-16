package com.ryan.controllers;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.ryan.data.UserDatabaseRepository;
import com.ryan.models.Result;
import com.ryan.models.User;
import com.ryan.service.UserService;
import com.ryan.util.ConnectionFactory;
import com.ryan.util.JWTBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class UserController {

	public static void register(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserService userService = new UserService(new UserDatabaseRepository(ConnectionFactory.getConnection()));
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(req.getReader());
			JsonNode firstNameNode = jsonNode.get("firstName");
			JsonNode lastNameNode = jsonNode.get("lastName");
			JsonNode emailNode = jsonNode.get("email");
			JsonNode passwordNode = jsonNode.get("password");
			User user = new User();
			if (firstNameNode != null && !(firstNameNode instanceof NullNode)) {
				user.setFirstName(firstNameNode.asText());
			}
			if (lastNameNode != null && !(lastNameNode instanceof NullNode)) {
				user.setLastName(lastNameNode.asText());
			}
			if (emailNode != null && !(emailNode instanceof NullNode)) {
				user.setEmail(emailNode.asText());
			}
			if (passwordNode != null && !(passwordNode instanceof NullNode)) {
				user.setPassword(passwordNode.asText());
			}
			Result<User> userResult = userService.register(user);
			Result<String> result = new Result<>();
			if (userResult.isSuccess()) {
				result.setPayload(JWTBuilder.buildJWT(userResult.getPayload()));
				res.setStatus(200);
			} else {
				for (Entry<String, String> entry : userResult.getMessages().entrySet()) {
					result.addMessage(entry.getKey(), entry.getValue());
				}
				res.setStatus(400);
			}
			res.getWriter().write(om.writeValueAsString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void login(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserService userService = new UserService(new UserDatabaseRepository(ConnectionFactory.getConnection()));
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(req.getReader());
			JsonNode emailNode = jsonNode.get("email");
			JsonNode passwordNode = jsonNode.get("password");
			User user = new User();
			if (emailNode != null && !(emailNode instanceof NullNode)) {
				user.setEmail(emailNode.asText());
			}
			if (passwordNode != null && !(passwordNode instanceof NullNode)) {
				user.setPassword(passwordNode.asText());
			}
			Result<User> userResult = userService.login(user);
			Result<String> result = new Result<>();
			if (userResult.isSuccess()) {
				result.setPayload(JWTBuilder.buildJWT(userResult.getPayload()));
				res.setStatus(200);
			} else {
				for (Entry<String, String> entry : userResult.getMessages().entrySet()) {
					result.addMessage(entry.getKey(), entry.getValue());
				}
				res.setStatus(400);
			}
			res.getWriter().write(om.writeValueAsString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUserInfo(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserService userService = new UserService(new UserDatabaseRepository(ConnectionFactory.getConnection()));
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(req.getReader());
			JsonNode userIdNode = jsonNode.get("userId");
			JsonNode firstNameNode = jsonNode.get("firstName");
			JsonNode lastNameNode = jsonNode.get("lastName");
			JsonNode emailNode = jsonNode.get("email");
			Jws<Claims> jws = JWTBuilder.parseJWS(req.getHeader("Authorization"));
			if (jws == null) {
				res.setStatus(401);
			} else {
				User user = new User();
				if (userIdNode != null && !(userIdNode instanceof NullNode)) {
					user.setUserId(userIdNode.asInt());
				}
				if (firstNameNode != null && !(firstNameNode instanceof NullNode)) {
					user.setFirstName(firstNameNode.asText());
				}
				if (lastNameNode != null && !(lastNameNode instanceof NullNode)) {
					user.setLastName(lastNameNode.asText());
				}
				if (emailNode != null && !(emailNode instanceof NullNode)) {
					user.setEmail(emailNode.asText());
				}
				int userId = (int) jws.getBody().get("userId");
				if (userId != user.getUserId()) {
					res.setStatus(403);
				} else {
					Result<User> result = userService.updateInfo(user);
					if (result.isSuccess()) {
						res.setStatus(200);
					} else {
						res.setStatus(400);
					}
					res.getWriter().write(om.writeValueAsString(result));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			res.setStatus(400);
		}
	}
	
	public static void updatePassword(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserService userService = new UserService(new UserDatabaseRepository(ConnectionFactory.getConnection()));
			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(req.getReader());
			JsonNode userIdNode = jsonNode.get("userId");
			JsonNode passwordNode = jsonNode.get("password");
			Jws<Claims> jws = JWTBuilder.parseJWS(req.getHeader("Authorization"));
			if (jws == null) {
				res.setStatus(401);
			} else {
				User user = new User();
				if (userIdNode != null && !(userIdNode instanceof NullNode)) {
					user.setUserId(userIdNode.asInt());
				}
				if (passwordNode != null && !(userIdNode instanceof NullNode)) {
					user.setPassword(passwordNode.asText());
				}
				int userId = (int) jws.getBody().get("userId");
				if (userId != user.getUserId()) {
					res.setStatus(403);
				} else {
					Result<User> result = userService.updatePassword(user);
					if (result.isSuccess()) {
						res.setStatus(200);
					} else {
						res.setStatus(400);
					}
					res.getWriter().write(om.writeValueAsString(result));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			res.setStatus(400);
		}
	}
}
