package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

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
	private String id;

	@JsonInclude(Include.NON_EMPTY)
	private String port;
	
	@JsonInclude(Include.NON_EMPTY)
	private String ip;
	
	private String model;
	private String brand;
	private Date createdAt;
	private Date updatedAt;

	public Reader(String port, String ip, String model, String brand) {
		this.port = port;
		this.ip = ip;
		this.model = model;
		this.brand = brand;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
}