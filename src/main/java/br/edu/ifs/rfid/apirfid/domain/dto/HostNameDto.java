package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HostNameDto {
	@NotBlank(message = "IP Addres is required")
	private String ip;
}
