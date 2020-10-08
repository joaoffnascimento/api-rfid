package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

	@Id
	private String id;
	private String epc;
	private String tipo;
	@Id
	private String activeId;
	
	private Date createdAt;
	private Date updatedAt;
	
	public Tag createTag(String epc, String tipo) {

		Tag tag = new Tag();

		tag.epc = epc;
		tag.tipo = tipo;
		tag.createdAt = new Date();
		tag.updatedAt = new Date();

		return tag;
	}
}
