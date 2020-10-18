package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DepartamentDto extends Entity {

	@NotBlank(message = "sigla is required")
	private String sigla;

	@NotBlank(message = "nome is required")
	private String nome;

	@NotBlank(message = "descricao is required")
	private String descricao;
}
