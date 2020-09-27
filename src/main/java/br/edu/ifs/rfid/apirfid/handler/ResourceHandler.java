package br.edu.ifs.rfid.apirfid.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.ifs.rfid.apirfid.exception.ReaderException;
import br.edu.ifs.rfid.apirfid.shared.ErrorResponse;
import br.edu.ifs.rfid.apirfid.shared.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {
	
	//Centralize all exceptions handler here
	
	//Spring calls automatically
	
	@ExceptionHandler(ReaderException.class)
	public ResponseEntity<ErrorResponse> handlerReaderException(ReaderException r){
		
		ErrorResponseBuilder error = ErrorResponse.builder();
		
		error.httpStatus(r.getHttpStatus().value());
		error.msg(r.getMessage());
		error.timeStamp(System.currentTimeMillis());
		
		return ResponseEntity.status(r.getHttpStatus()).body(error.build());
		
	}
}
