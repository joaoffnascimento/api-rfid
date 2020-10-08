package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	private ObjectId id;
	private int matSiape;
	private String nome;
	private String senha;
	private String profissao;
	private Date inicioExercicio;
	private ObjectId occupationId;
	private ObjectId departamentId;
}
