package br.edu.ifs.rfid.apirfid.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import lombok.Data;

@Data
public class FnUtil {

	@SuppressWarnings("unused")
	public String toLikeRegex(String source) {

		source = "*" + source + "*";

		return source.replaceAll("\\*", ".*");
	}

	public static Boolean isToMove(Boolean calculaTempo, long timeSinceLastMove) {

		if (!calculaTempo && timeSinceLastMove < 5) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	public static long diferencaEmMinutos(MovementHistory historico) {
		LocalDateTime dataHoraAtual = LocalDateTime.now();

		String dataEmString = historico.getDataHoraMovimentacaoEmString();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dataHoraRegistro = LocalDateTime.parse(dataEmString, formatter);

		return ChronoUnit.MINUTES.between(dataHoraRegistro, dataHoraAtual);
	}
}
