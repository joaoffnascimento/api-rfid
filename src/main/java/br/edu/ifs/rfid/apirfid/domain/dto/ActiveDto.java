package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ActiveDto extends Entity {

	@Min(value = 1, message = "numeroPatrimonio number is required")
	@Max(value = 10000, message = "numeroPatrimonio number is required")
	private int numeroPatrimonio;

	@NotBlank(message = "Nome Host required")
	private String nomeHost;

	@NotBlank(message = "Marca is required")
	private String marca;

	@NotBlank(message = "Modelo is required")
	private String modelo;

	private Date dataAquisicao;
	private Date dataFinalGarantia;

	@NotBlank(message = "hasGarantia Addres is required")
	private Boolean hasGarantia;
}
