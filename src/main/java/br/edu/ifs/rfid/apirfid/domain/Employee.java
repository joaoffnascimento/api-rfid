package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employee extends Entity {

	@Id
	private String id;

	@NotBlank(message = "MatSiape is required")
	private String matSiape;

	@NotBlank(message = "Nome is required")
	private String nome;

	private String profissao;

	@NotNull(message = "Inicio Exercicio is required")
	private Date inicioExercicio;

	@NotBlank(message = "Occupation ID is required")
	private String occupationId;

	@NotBlank(message = "Department ID is required")
	private String departamentId;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	private String token;
}
