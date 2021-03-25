package com.ryan.test;

import static org.junit.Assert.assertEquals;

import java.security.Key;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.models.User;
import com.ryan.util.JWTBuilder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTBuilderTest {
	private static Key key;
	
	@BeforeClass
	public static void setup() {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET_STRING")));
	}
	
	@Test
	public void shouldBuildJWT() {
		User user = new User();
		user.setEmail("test@test.com");
		String jws = JWTBuilder.buildJWT(user);
		String decodedEmail = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();
		assertEquals("test@test.com", decodedEmail);
	}

}
