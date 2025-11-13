package br.edu.ifs.rfid.apirfid.shared;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Auth {

	private static final String TOKEN_BEARER = "Bearer ";

	private static String tokenSecret;
	private static long tokenExpirationMinutes;

	@Value("${jwt.secret}")
	public void setTokenSecret(String secret) {
		Auth.tokenSecret = secret;
	}

	@Value("${jwt.expiration-minutes}")
	public void setTokenExpirationMinutes(long expirationMinutes) {
		Auth.tokenExpirationMinutes = expirationMinutes;
	}

	public static String createToken(Claims claims) {
		final Instant tokenExpirationTime = Instant.now().plus(tokenExpirationMinutes, ChronoUnit.MINUTES);
		return Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(tokenExpirationTime))
				.signWith(SignatureAlgorithm.HS256, tokenSecret)
				.compact();
	}

	public static Claims decodeToken(final String authHeader) throws IllegalAccessException {
		if (authHeader == null || authHeader.trim().isEmpty() || !authHeader.startsWith(TOKEN_BEARER)) {
			throw new IllegalAccessException("Bad Authorization");
		}
		final String token = authHeader.split(" ")[1];
		return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
	}
}