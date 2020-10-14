package br.edu.ifs.rfid.apirfid.domain.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import br.edu.ifs.rfid.apirfid.domain.Active;
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
	
	@NotBlank(message = "ActiveCategory is required")
	private String activeCategoryId;
	
	@NotBlank(message = "TagId is required")
	private String tagId;

	@NotBlank(message = "Marca is required")
	private String marca;

	@NotBlank(message = "Modelo is required")
	private String modelo;

	private Date dataAquisicao;
	private Date dataFinalGarantia;
	
	private Boolean hasGarantia;

	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;
	
	private int lastMovimentacao;
	
	public ActiveDto createActive(int numeroPatrimonio, String marca, String modelo, Date dataAquisicao,
			Date dataFinalGarantia, Boolean hasGarantia, String activeCategoryId, String tagId) {

		ActiveDto activeDto = new ActiveDto();

		activeDto.numeroPatrimonio = numeroPatrimonio;
		activeDto.marca = marca;
		activeDto.modelo = modelo;
		activeDto.dataAquisicao = dataAquisicao;
		activeDto.dataFinalGarantia = dataFinalGarantia;
		activeDto.hasGarantia = hasGarantia;
		
		activeDto.activeCategoryId = activeCategoryId;
		activeDto.tagId = tagId;

		activeDto.lastMovimentacao = 0;
		
		activeDto.dtAquisTMSTMP = System.currentTimeMillis();
		activeDto.dtFinalTMSTMP = System.currentTimeMillis();

		return activeDto;
	}
}
