package br.edu.ifs.rfid.apirfid.shared;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMapResponse {

	private Map<String, String> errors;
	private int httpStatus;
	private long timeStamp;
	
}
