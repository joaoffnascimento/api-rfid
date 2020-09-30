package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReaderDto {

	private ObjectId id;

	@Min(value = 1, message = "Port number is required")
	@Max(value = 10000, message = "Port number is required")
	private int port;

	@NotBlank(message = "IP Addres is required")
	private String ip;
	private String model;
	private String brand;

	private Date createdAt;
	private Date updatedAt;
}
