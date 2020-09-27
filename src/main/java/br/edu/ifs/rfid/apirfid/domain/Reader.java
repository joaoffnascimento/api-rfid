package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

	@Id
	private ObjectId id;

	@JsonInclude(Include.NON_EMPTY)
	private int port;

	@JsonInclude(Include.NON_EMPTY)
	private String ip;

	private String model;
	private String brand;
	private Date createdAt;
	private Date updatedAt;

	public Reader(int port, String ip, String model, String brand) {
		this.port = port;
		this.ip = ip;
		this.model = model;
		this.brand = brand;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	public Reader createReader(int port, String ip, String model, String brand) {

		Reader reader = new Reader();

		reader.id = new ObjectId();

		reader.port = port;
		reader.ip = ip;
		reader.model = model;
		reader.brand = brand;
		reader.createdAt = new Date();
		reader.updatedAt = new Date();

		return reader;
	}
}