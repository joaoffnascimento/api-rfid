package br.edu.ifs.rfid.apirfid.domain;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveCategory {

	@Id
	private int id;
	private String sigla;
	private String descricao;
}
