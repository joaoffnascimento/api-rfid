package br.edu.ifs.rfid.apirfid.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.ifs.rfid.apirfid.exception.ReaderException;
import br.edu.ifs.rfid.apirfid.shared.ErrorMapResponse;
import br.edu.ifs.rfid.apirfid.shared.ErrorMapResponse.ErrorMapResponseBuilder;
import br.edu.ifs.rfid.apirfid.shared.ErrorResponse;
import br.edu.ifs.rfid.apirfid.shared.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {

	// Centralize all exceptions handler here

	// Spring calls automatically

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMapResponse> MethodArgumentNotValidException(MethodArgumentNotValidException r) {

		Map<String, String> errors = new HashMap<>();

		r.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(field, message);
		});

		ErrorMapResponseBuilder error = ErrorMapResponse.builder();

		error.errors(errors).httpStatus(HttpStatus.BAD_REQUEST.value()).timeStamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.build());

	}

	@ExceptionHandler(ReaderException.class)
	public ResponseEntity<ErrorResponse> handlerReaderException(ReaderException r) {

		ErrorResponseBuilder error = ErrorResponse.builder();

		error.httpStatus(r.getHttpStatus().value());
		error.msg(r.getMessage());
		error.timeStamp(System.currentTimeMillis());

		return ResponseEntity.status(r.getHttpStatus()).body(error.build());

	}
}
