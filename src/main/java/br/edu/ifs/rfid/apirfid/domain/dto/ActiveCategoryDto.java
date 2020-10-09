package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ActiveCategoryDto extends Entity {

	@NotBlank(message = "Sigla is required")
	private String sigla;
	@NotBlank(message = "Descricao is required")
	private String descricao;
	@NotBlank(message = "tipo is required")
	private String tipo;
}
