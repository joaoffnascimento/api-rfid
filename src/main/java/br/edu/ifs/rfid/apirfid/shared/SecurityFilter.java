package br.edu.ifs.rfid.apirfid.shared;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class SecurityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final Claims claims;

		try {
			claims = Auth.decodeToken(authHeader);
		} catch (IllegalAccessException e) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
			return;
		} catch (ExpiredJwtException e) {
			response.sendError(HttpStatus.REQUEST_TIMEOUT.value(),
					"Opa, identificamos que você está a algum tempo sem acessar o sistema. Para sua segurança, logue novamente.");
			return;
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
			return;
		}

		refreshToken(response, claims);
		chain.doFilter(servletRequest, servletResponse);
	}

	private void refreshToken(HttpServletResponse response, Claims claims) {
		String token = Auth.createToken(claims);
		response.setHeader(HttpHeaders.AUTHORIZATION, token);
	}
}