package br.edu.ifs.rfid.apirfid.domain;

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
public class Departament extends Entity {

	private String sigla;
	private String nome;
	private String descricao;

	public Departament createDepartament(String sigla, String nome, String descricao) {
		Departament departament = new Departament();

		departament.sigla = sigla;
		departament.nome = nome;
		departament.descricao = descricao;

		return departament;
	}
}
