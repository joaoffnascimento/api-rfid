package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementHistoryDto {

	private Date dataHoraMovimentacao;
	@Min(value = 0, message = "tipoMovimentacao number is required")
	@Max(value = 1, message = "tipoMovimentacao number is required")
	private int tipoMovimentacao;

	private ObjectId activeId;

}
