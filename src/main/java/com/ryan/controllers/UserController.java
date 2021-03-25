package com.ryan.controllers;

import java.io.IOException;

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

public class UserController {

	public static void register(HttpServletRequest req, HttpServletResponse res) {
		try {
			UserService service = new UserService(new UserDatabaseRepository(ConnectionFactory.getConnection()));
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
			Result<User> result = service.register(user);
			res.setStatus(200);
			res.getWriter().write(om.writeValueAsString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
