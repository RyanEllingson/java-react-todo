package com.ryan.util;

import java.security.Key;

import com.ryan.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTBuilder {
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public static String buildJWT(User user) {
		String jws = Jwts.builder().setSubject(user.getEmail()).signWith(key).compact();
		return jws;
	}

}
