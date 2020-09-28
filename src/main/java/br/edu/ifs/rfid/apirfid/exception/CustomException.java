package br.edu.ifs.rfid.apirfid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -8615245851861457216L;

	private final HttpStatus httpStatus;

	public CustomException(final String msg, final HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
}
