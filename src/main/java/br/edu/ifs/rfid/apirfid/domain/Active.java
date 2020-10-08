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
	private String locationId;
	@Id
	private String activeCategoryId;

	private int numeroPatrimonio;
	private String nomeHost;
	private String marca;
	private String modelo;
	private Date dataAquisicao;
	private Date dataFinalGarantia;
	private Boolean hasGarantia;
	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;
	private int lastMovimentacao;

	public Active createActive(int numeroPatrimonio, String nomeHost, String marca, String modelo, Date dataAquisicao,
			Date dataFinalGarantia, Boolean hasGarantia) {

		Active active = new Active();

		active.numeroPatrimonio = numeroPatrimonio;
		active.nomeHost = nomeHost;
		active.marca = marca;
		active.modelo = modelo;
		active.dataAquisicao = dataAquisicao;
		active.dataFinalGarantia = dataFinalGarantia;
		active.hasGarantia = hasGarantia;

		active.lastMovimentacao = 0;
		
		active.dtAquisTMSTMP = System.currentTimeMillis();
		active.dtFinalTMSTMP = System.currentTimeMillis();

		return active;
	}
}
