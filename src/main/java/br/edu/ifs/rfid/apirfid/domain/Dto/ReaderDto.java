package br.edu.ifs.rfid.apirfid.domain.Dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReaderDto {

	private ObjectId id;

	@PositiveOrZero(message = "Port number is required")
	private int port;

	@NotBlank(message = "IP Addres is required")
	private String ip;
	private String model;
	private String brand;
	
	private Date createdAt;
	private Date updatedAt;
}
