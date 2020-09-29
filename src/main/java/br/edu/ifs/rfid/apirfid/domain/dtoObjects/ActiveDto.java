package br.edu.ifs.rfid.apirfid.domain.dtoObjects;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiveDto {
	
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
