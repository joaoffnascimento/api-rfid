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
public class ActiveCategory extends Entity {

	private String sigla;
	private String descricao;
	private String tipo;

	public ActiveCategory createActiveCategory(String sigla, String descricao, String tipo) {

		ActiveCategory activeCategory = new ActiveCategory();

		activeCategory.sigla = sigla;
		activeCategory.descricao = descricao;
		activeCategory.tipo = tipo;

		return activeCategory;
	}
}
