package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;
import java.util.List;

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
public class Occupation {
	@Id
	private ObjectId id;
	private String descricao;
	private Date dataInicioFuncao;
	private List<Employee> listEmployers;
}
