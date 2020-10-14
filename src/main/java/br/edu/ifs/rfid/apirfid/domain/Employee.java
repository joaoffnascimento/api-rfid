package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import org.bson.types.ObjectId;
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

	private int matSiape;
	private String nome;
	private String profissao;
	private Date inicioExercicio;
	private String occupationId;
	private String departamentId;
	
	public Employee create(int matSiape, String nome, String profissao, Date inicioExercicio, String occupationId, String departamentId) {
		Employee employee = new Employee();
		
		employee.matSiape = matSiape;
		employee.nome = nome;
		employee.profissao = profissao;
		employee.inicioExercicio = inicioExercicio;
		employee.occupationId = occupationId;
		employee.departamentId = departamentId;
		
		return employee;
	}
}
