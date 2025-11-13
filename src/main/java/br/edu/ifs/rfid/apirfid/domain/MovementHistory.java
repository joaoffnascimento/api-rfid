package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MovementHistory extends Entity {

	@Id
	private String activeId;
	private Date dataHoraMovimentacao;
	private int tipoMovimentacao;
	private String numPatrimonio;
	private List<Active> actives;

	private static final java.time.format.DateTimeFormatter FORMATTER =
			java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public String getDataHoraMovimentacaoEmString() {
		if (this.dataHoraMovimentacao == null) {
			return null;
		}
		return this.dataHoraMovimentacao.toInstant()
				.atZone(java.time.ZoneId.systemDefault())
				.toLocalDateTime()
				.format(FORMATTER);
	}
}
