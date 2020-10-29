package br.edu.ifs.rfid.apirfid.shared;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class Auth {

	private static final String TOKEN_SECRET = "4a1a71a9ebb86dad178c14efb6wed2";
	private static final String TOKEN_BEARER = "Bearer ";

	private Auth() {
	}

	public static String createToken(Claims claims) {
		final Instant tokenExpirationTime = Instant.now().plus(99999999, ChronoUnit.MINUTES);
		return Jwts.builder().setId(UUID.randomUUID().toString()).setClaims(claims).setIssuedAt(new Date())
				.setExpiration(Date.from(tokenExpirationTime)).signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
				.compact();
	}

	public static Claims decodeToken(final String authHeader) throws IllegalAccessException {
		if (authHeader == null || !authHeader.startsWith(TOKEN_BEARER)) {
			throw new IllegalAccessException("Bad Authorization");
		}
		final String token = authHeader.split(" ")[1];
		return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
	}
}