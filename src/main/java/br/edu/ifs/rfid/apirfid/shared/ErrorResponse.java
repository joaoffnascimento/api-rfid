package br.edu.ifs.rfid.apirfid.shared;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

	//Immutable values;
	
	private String msg;
	private int httpStatus;
	private long timeStamp;
	
}
