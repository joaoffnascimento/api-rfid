package br.edu.ifs.rfid.apirfid.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	private int numPatrimonio;

	public MovementHistory createMovement(int tipoMovimentacao, String activeId, int numPatrimonio) {

		MovementHistory movementHistory = new MovementHistory();

		movementHistory.activeId = activeId;

		movementHistory.tipoMovimentacao = tipoMovimentacao;
		movementHistory.activeId = activeId;
		movementHistory.dataHoraMovimentacao = new Date();
		movementHistory.numPatrimonio = numPatrimonio;

		return movementHistory;
	}

	public String getDataHoraMovimentacaoEmString() {
		SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatarData.format(this.dataHoraMovimentacao);
	}
}
