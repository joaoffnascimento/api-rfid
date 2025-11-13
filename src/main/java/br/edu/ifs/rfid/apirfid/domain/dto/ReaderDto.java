package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReaderDto {

	@Min(value = 1, message = "Port number is required")
	@Max(value = 10000, message = "Port number is required")
	private int port;

	@NotBlank(message = "IP Addres is required")
	private String ip;
	private String model;
	private String brand;

}
