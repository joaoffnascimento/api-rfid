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
import br.edu.ifs.rfid.apirfid.shared.Response;

@ControllerAdvice
public class ResourceHandler {

	// Centralize all exceptions handler here

	// Spring calls automatically

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response<Map<String, String>>> methodArgumentNotValidException(
			MethodArgumentNotValidException r) {

		Map<String, String> errors = new HashMap<>();

		r.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(field, message);
		});

		Response<Map<String, String>> response = new Response<>(false);

		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setData(errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}

	@ExceptionHandler(ReaderException.class)
	public ResponseEntity<Response<String>> handlerReaderException(ReaderException r) {

		Response<String> response = new Response<>(false);

		response.setStatusCode(r.getHttpStatus().value());
		response.setData(r.getMessage());

		return ResponseEntity.status(r.getHttpStatus()).body(response);

	}
}
