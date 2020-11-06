package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ActiveDto extends Entity {

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

	private Date dataAquisicao;
	private Date dataFinalGarantia;

	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;

	private int lastMovimentacao;

	public ActiveDto createActive(String numeroPatrimonio, String marca, String modelo, Date dataAquisicao,
			Date dataFinalGarantia, String activeCategoryId, String departamentId, String tagId) {

		ActiveDto activeDto = new ActiveDto();

		activeDto.numeroPatrimonio = numeroPatrimonio;
		activeDto.marca = marca;
		activeDto.modelo = modelo;
		activeDto.dataAquisicao = dataAquisicao;
		activeDto.dataFinalGarantia = dataFinalGarantia;

		activeDto.departamentId = departamentId;
		activeDto.activeCategoryId = activeCategoryId;
		activeDto.tagId = tagId;

		activeDto.lastMovimentacao = 0;

		activeDto.dtAquisTMSTMP = System.currentTimeMillis();
		activeDto.dtFinalTMSTMP = System.currentTimeMillis();

		return activeDto;
	}
}
