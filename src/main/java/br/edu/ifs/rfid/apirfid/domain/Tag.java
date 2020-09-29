package br.edu.ifs.rfid.apirfid.domain;

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
public class Tag {

	@Id
	private ObjectId id;
	private String epc;
	private String tipo;

	private Active active;
}
