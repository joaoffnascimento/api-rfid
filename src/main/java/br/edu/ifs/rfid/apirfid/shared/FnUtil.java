package br.edu.ifs.rfid.apirfid.shared;

import lombok.Data;

@Data
public class FnUtil {

	@SuppressWarnings("unused")
	public String toLikeRegex(String source) {

		source = "*" + source + "*";

		return source.replaceAll("\\*", ".*");
	}
	
	public static Boolean isToMove(Boolean calculaTempo, long timeSinceLastMove) {
		
		if(!calculaTempo && timeSinceLastMove < 5) {
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
}
