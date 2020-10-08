package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TagDto extends Entity {

	@NotBlank(message = "epc Addres is required")
	private String epc;
	@NotBlank(message = "tipo Addres is required")
	private String tipo;
	@Id
	private String activeId;
}
