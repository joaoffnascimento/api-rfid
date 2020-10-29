package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

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
	private String tagId;
	@Id
	private String departamentId;
	@Id
	private String activeCategoryId;

	private String numeroPatrimonio;
	private String marca;
	private String modelo;
	private Date dataAquisicao;
	private Date dataFinalGarantia;
	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;
	private int lastMovimentacao;

	public Active createActive(String numeroPatrimonio, String marca, String modelo, Date dataAquisicao,
			Date dataFinalGarantia, String activeCategoryId, String departamentId, String tagId) {

		Active active = new Active();

		active.numeroPatrimonio = numeroPatrimonio;
		active.marca = marca;
		active.modelo = modelo;
		active.dataAquisicao = dataAquisicao;
		active.dataFinalGarantia = dataFinalGarantia;

		active.activeCategoryId = activeCategoryId;
		active.departamentId = departamentId;
		active.tagId = tagId;

		active.lastMovimentacao = 0;

		active.dtAquisTMSTMP = System.currentTimeMillis();
		active.dtFinalTMSTMP = System.currentTimeMillis();

		return active;
	}
}
