package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDto {

	@Id
	private String id;
	@NotBlank(message = "epc Addres is required")
	private String epc;
	@NotBlank(message = "tipo Addres is required")
	private String tipo;
	@Id
	private String activeId;

	private Date createdAt;
	private Date updatedAt;
}
