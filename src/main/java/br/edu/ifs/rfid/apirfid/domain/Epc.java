package br.edu.ifs.rfid.apirfid.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "epc")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Epc extends Entity {

	private String epcValue;

	public Epc createEpc(String epcValue) {

		Epc epcObj = new Epc();

		epcObj.epcValue = epcValue;

		return epcObj;
	}
}
