package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import br.edu.ifs.rfid.apirfid.shared.FnUtil;
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

	private String matSiape;
	private String nome;
	private String profissao;
	private Date inicioExercicio;
	private String occupationId;
	private String departamentId;

	private String email;
	private String password;
	private String token;

	public Employee create(String matSiape, String nome, String profissao, Date inicioExercicio, String occupationId,
			String departamentId, String email, String password) {
		Employee employee = new Employee();

		employee.matSiape = matSiape;
		employee.nome = nome;
		employee.profissao = profissao;
		employee.inicioExercicio = inicioExercicio;
		employee.occupationId = occupationId;
		employee.departamentId = departamentId;

		employee.email = email;
		employee.password = FnUtil.bcryptEncode(password);

		return employee;
	}
}
