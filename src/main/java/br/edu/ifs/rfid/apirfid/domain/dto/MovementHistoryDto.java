package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementHistoryDto {

	private Date dataHoraMovimentacao;
	@Min(value = 0, message = "tipoMovimentacao number is required")
	@Max(value = 1, message = "tipoMovimentacao number is required")
	private int tipoMovimentacao;

	@Id
	private String activeId;

}
