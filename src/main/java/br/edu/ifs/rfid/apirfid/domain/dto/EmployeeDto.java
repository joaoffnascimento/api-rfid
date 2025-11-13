package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeDto {

	@NotBlank(message = "matSiape is required")
	private String matSiape;

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

	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;
}
