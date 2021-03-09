package com.ryan.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
	private static Connection conn;
	private static Environment environment;
	
	public static void setEnvironment(Environment env) {
		if (env != environment && conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		environment = env;
	}
	
	private static void configure() {
		if (environment == null) {
			setEnvironment(Environment.DEVELOPMENT);
		}
		
		try {
			String username = null;
			String password = null;
			String dbUrl = null;
			URI dbUri = null;
			if (conn != null) {
				conn.close();
			}
			switch(environment) {
			case PRODUCTION:
				dbUri = new URI(System.getenv("DATABASE_URL"));
				
				username = dbUri.getUserInfo().split(":")[0];
				password = dbUri.getUserInfo().split(":")[1];
				dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
				break;
			case TEST:
				dbUri = new URI(System.getenv("HEROKU_POSTGRESQL_MAUVE_URL"));
				username = dbUri.getUserInfo().split(":")[0];
				password = dbUri.getUserInfo().split(":")[1];
				dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
				break;
			case DEVELOPMENT:
				username = "postgres";
				password = "postgres";
				dbUrl = "jdbc:postgresql://localhost/postgres?current_schema=java_react_todo";
			}
			conn = DriverManager.getConnection(dbUrl, username, password);
		} catch (URISyntaxException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if (conn == null) {
			configure();
		}
		return conn;
	}
	
	public static void restoreKnownGoodState() {
		if (environment == Environment.TEST) {
			if (conn == null) {
				configure();
			}
			String sql = "call known_good_state()";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
