package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActiveCategoryDto {

	@NotBlank(message = "Sigla is required")
	private String sigla;
	@NotBlank(message = "Descricao is required")
	private String descricao;
	@NotBlank(message = "tipo is required")
	private String tipo;
}
