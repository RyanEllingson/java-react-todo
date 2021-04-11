package com.ryan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ryan.models.User;
import com.ryan.util.JWTBuilder;

import io.jsonwebtoken.Claims;
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
		user.setFirstName("first name");
		user.setLastName("last name");
		user.setEmail("test@test.com");
		String jws = JWTBuilder.buildJWT(user);
		Claims jwtBody = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody();
		assertEquals("first name", jwtBody.get("firstName"));
		assertEquals("last name", jwtBody.get("lastName"));
		assertEquals("test@test.com", jwtBody.getSubject());
		Instant expectedExpiration = Instant.now().plus(15, ChronoUnit.MINUTES);
		long expirationDiffSeconds = jwtBody.getExpiration().toInstant().until(expectedExpiration, ChronoUnit.SECONDS);
		assertTrue(Math.abs(expirationDiffSeconds) < 5L);
	}

}
