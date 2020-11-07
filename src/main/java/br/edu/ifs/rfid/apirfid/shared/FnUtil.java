package br.edu.ifs.rfid.apirfid.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import io.jsonwebtoken.Jwts;
import lombok.Data;

@Data
public class FnUtil {

	private static final BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

	@SuppressWarnings("unused")
	public String toLikeRegex(String source) {

		source = "*" + source + "*";

		return source.replaceAll("\\*", ".*");
	}

	public static Boolean isToMove(Boolean calculaTempo, long timeSinceLastMove) {

		if (calculaTempo && timeSinceLastMove > 5) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	public static long diferencaEmMinutos(MovementHistory historico) {
		LocalDateTime dataHoraAtual = LocalDateTime.now();

		String dataEmString = historico.getDataHoraMovimentacaoEmString();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dataHoraRegistro = LocalDateTime.parse(dataEmString, formatter);

		return ChronoUnit.MINUTES.between(dataHoraRegistro, dataHoraAtual);
	}

	public static String bcryptEncode(String data) {
		return pwdEncoder.encode(data);
	}

	public static Boolean bcryptMatch(String data, String encoded) {
		return pwdEncoder.matches(data, encoded);
	}

	public static String createToken(UserDto user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("usuarioSistema", user);
		return Auth.createToken(Jwts.claims(claims));
	}
}
