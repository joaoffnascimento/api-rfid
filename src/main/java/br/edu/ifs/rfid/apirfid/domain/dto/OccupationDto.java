package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OccupationDto extends Entity {

	@NotBlank(message = "funcao is required")
	private String funcao;
	@NotBlank(message = "descricao is required")
	private String descricao;
	private Date dataInicioFuncao;
}
