package br.edu.ifs.rfid.apirfid.shared;

import lombok.Data;

@Data
public class FnUtil {

	@SuppressWarnings("unused")
	public String toLikeRegex(String source) {

		source = "*" + source + "*";

		return source.replaceAll("\\*", ".*");
	}
}
