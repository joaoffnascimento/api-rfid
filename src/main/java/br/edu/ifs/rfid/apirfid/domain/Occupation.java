package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

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
public class Occupation extends Entity {

	private String funcao;
	private String descricao;
	private Date dataInicioFuncao;

	public Occupation createOccupation(String funcao, String descricao, Date dataInicioFuncao) {
		Occupation occupation = new Occupation();

		occupation.funcao = funcao;
		occupation.descricao = descricao;
		occupation.dataInicioFuncao = dataInicioFuncao;

		return occupation;
	}
}
