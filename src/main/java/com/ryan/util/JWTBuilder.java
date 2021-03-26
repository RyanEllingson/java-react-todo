package com.ryan.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.ryan.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTBuilder {
	
	public static String buildJWT(User user) {
		Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET_STRING")));
		Date expiration = new Date(Instant.now().plus(15, ChronoUnit.MINUTES).getEpochSecond() * 1000);
		String jws = Jwts.builder().setSubject(user.getEmail()).setExpiration(expiration).claim("firstName", user.getFirstName()).claim("lastName", user.getLastName()).signWith(key).compact();
		return jws;
	}
}
