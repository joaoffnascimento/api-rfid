package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveDto {

	@NotBlank(message = "numeroPatrimonio number is required")
	private String numeroPatrimonio;

	@NotBlank(message = "ActiveCategory is required")
	private String activeCategoryId;

	@NotBlank(message = "departamentId is required")
	private String departamentId;

	@NotBlank(message = "TagId is required")
	private String tagId;

	@NotBlank(message = "Marca is required")
	private String marca;

	@NotBlank(message = "Modelo is required")
	private String modelo;

	@NotNull(message = "Data Aquisicao is required")
	private Date dataAquisicao;

	private Date dataFinalGarantia;
	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;
	private int lastMovimentacao;
}
