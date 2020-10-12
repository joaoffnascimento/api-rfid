package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeDto extends Entity {

	@Min(value = 1, message = "numeroPatrimonio number is required")
	@Max(value = 1000000000, message = "numeroPatrimonio number is required")
	private int matSiape;

	@NotBlank(message = "Nome is required")
	private String nome;

	@NotBlank(message = "Profissao is required")
	private String profissao;

	@NotNull(message = "inicioExercio is required")
	private Date inicioExercicio;

	@NotBlank(message = "occupationId is required")
	private String occupationId;

	@NotBlank(message = "departamentId is required")
	private String departamentId;

}
