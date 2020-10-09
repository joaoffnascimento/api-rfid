package br.edu.ifs.rfid.apirfid.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Reader extends Entity {

	@JsonInclude(Include.NON_EMPTY)
	private int port;

	@JsonInclude(Include.NON_EMPTY)
	private String ip;

	private String model;
	private String brand;

	public Reader createReader(int port, String ip, String model, String brand) {

		Reader reader = new Reader();

		reader.port = port;
		reader.ip = ip;
		reader.model = model;
		reader.brand = brand;

		return reader;
	}
}