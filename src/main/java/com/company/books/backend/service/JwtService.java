package com.company.books.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	  private static final int SECRET_KEY_LENGTH = 32;
	
	  private static final String JWT_SECRET_KEY = generateRandomKey(SECRET_KEY_LENGTH);//llave para armar el token

	  public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 1; // 1 hora

		// Función para generar una clave secreta aleatoria en Base64
		private static String generateRandomKey(int size) {
			SecureRandom secureRandom = new SecureRandom();
			byte[] randomBytes = new byte[size];
			secureRandom.nextBytes(randomBytes);
			String key = Base64.getEncoder().encodeToString(randomBytes);
			System.out.println(key);
			return key; // Retorna la clave en Base64
		}

	  public String extractUsername(String token) {
	    return extractClaim(token, Claims::getSubject);
	  }

	  public Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	  }

	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    return claimsResolver.apply(extractAllClaims(token));
	  }

	  private Claims extractAllClaims(String token) {
	    return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
	  }

	  private Boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	  }

	  public String generateToken(UserDetails userDetails) {
	    Map<String, Object> claims = new HashMap<>();
	    var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
	    claims.put("rol", rol);
	    return createToken(claims, userDetails.getUsername());
	  }

	  private String createToken(Map<String, Object> claims, String subject) {

	    return Jwts
	        .builder()
	        .setClaims(claims)
	        .setSubject(subject)
	        .setIssuedAt(new Date(System.currentTimeMillis()))
	        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
	        .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
	        .compact();
	  }

	  public boolean validateToken(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }

}
