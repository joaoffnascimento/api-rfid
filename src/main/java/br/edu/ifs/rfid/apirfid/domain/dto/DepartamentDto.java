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
public class DepartamentDto {

	@NotBlank(message = "sigla is required")
	private String sigla;

	@NotBlank(message = "nome is required")
	private String nome;

	@NotBlank(message = "descricao is required")
	private String descricao;
}
