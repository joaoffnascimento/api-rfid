package br.edu.ifs.rfid.apirfid.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementHistory {

	@Id
	private String id;
	private Date dataHoraMovimentacao;
	private Date createdAt;
	private Date updatedAt;
	private int tipoMovimentacao;
	private int numPatrimonio;
	@Id
	private String activeId;

	public MovementHistory createMovement(int tipoMovimentacao, String activeId, int numPatrimonio) {

		MovementHistory movementHistory = new MovementHistory();

		movementHistory.tipoMovimentacao = tipoMovimentacao;
		movementHistory.activeId = activeId;
		movementHistory.dataHoraMovimentacao = new Date();
		movementHistory.numPatrimonio = numPatrimonio;
		movementHistory.createdAt = new Date();
		movementHistory.updatedAt = new Date();

		return movementHistory;
	}

	public String getDataHoraMovimentacaoEmString() {
		SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatarData.format(this.dataHoraMovimentacao);
	}
}
