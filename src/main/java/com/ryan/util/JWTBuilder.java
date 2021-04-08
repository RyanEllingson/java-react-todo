package com.ryan.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.ryan.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTBuilder {
	
	public static String buildJWT(User user) {
		Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET_STRING")));
		Date expiration = new Date(Instant.now().plus(15, ChronoUnit.MINUTES).getEpochSecond() * 1000);
		String jws = Jwts.builder().setSubject(user.getEmail()).setExpiration(expiration).claim("userId", user.getUserId()).claim("firstName", user.getFirstName()).claim("lastName", user.getLastName()).signWith(key).compact();
		return jws;
	}
	
	public static Jws<Claims> parseJWS(String jwsString) {
		Jws<Claims> jws = null;
		Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET_STRING")));
		try {
			jws = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwsString);
		} catch (JwtException e) {
			
		}
		return jws;
	}
}
