package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OccupationDto {

	@NotBlank(message = "funcao is required")
	private String funcao;
	@NotBlank(message = "descricao is required")
	private String descricao;
	private Date dataInicioFuncao;
}
