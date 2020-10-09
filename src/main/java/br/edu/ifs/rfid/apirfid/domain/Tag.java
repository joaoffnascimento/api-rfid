package br.edu.ifs.rfid.apirfid.domain;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag extends Entity {

	@Id
	private String activeId;
	private String epc;
	private String tipo;

	public Tag createTag(String epc, String tipo) {

		Tag tag = new Tag();

		tag.epc = epc;
		tag.tipo = tipo;

		return tag;
	}
}
