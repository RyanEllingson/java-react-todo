package com.ryan.util;

import java.security.Key;

import com.ryan.models.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTBuilder {
	
	public static String buildJWT(User user) {
		Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET_STRING")));
		String jws = Jwts.builder().setSubject(user.getEmail()).signWith(key).compact();
		try {
			System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject());
		} catch (JwtException e) {
			e.printStackTrace();
		}
		return jws;
	}
}
