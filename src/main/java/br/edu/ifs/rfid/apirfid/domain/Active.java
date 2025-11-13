package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Active extends Entity {

	@Id
	private String id;

	@NotBlank(message = "Tag ID is required")
	private String tagId;

	@NotBlank(message = "Department ID is required")
	private String departamentId;

	@NotBlank(message = "Active Category ID is required")
	private String activeCategoryId;

	@NotBlank(message = "Numero Patrimonio is required")
	private String numeroPatrimonio;

	private String marca;
	private String modelo;

	@NotNull(message = "Data Aquisicao is required")
	private Date dataAquisicao;

	private Date dataFinalGarantia;
	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;
	private int lastMovimentacao;
}
