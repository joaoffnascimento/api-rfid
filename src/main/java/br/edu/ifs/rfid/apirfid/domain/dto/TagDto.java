package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TagDto {

	@NotBlank(message = "epc Addres is required")
	private String epc;
	@NotBlank(message = "tipo Addres is required")
	private String tipo;
	@Id
	private String activeId;
}
