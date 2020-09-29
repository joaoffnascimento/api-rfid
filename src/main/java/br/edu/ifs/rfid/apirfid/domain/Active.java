package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Active {

	@Id
	private ObjectId id;

	private int numeroPatrimonio;
	private String nomeHost;
	private String marca;
	private String modelo;
	private Date dataAquisicao;
	private Date dataFinalGarantia;
	private Boolean hasGarantia;

	private float dtAquisTMSTMP;
	private float dtFinalTMSTMP;

	private Tag tag;
	private Location location;
	private ActiveCategory activeCategory;

	private Date createdAt;
	private Date updatedAt;

	private List<MovementHistory> listMovementHistory;

	public Active createActive(int numeroPatrimonio, String nomeHost, String marca, String modelo, Date dataAquisicao,
			Date dataFinalGarantia, Boolean hasGarantia) {

		Active active = new Active();

		active.id = new ObjectId();

		active.numeroPatrimonio = numeroPatrimonio;
		active.nomeHost = nomeHost;
		active.marca = marca;
		active.modelo = modelo;
		active.dataAquisicao = dataAquisicao;
		active.dataFinalGarantia = dataFinalGarantia;
		active.hasGarantia = hasGarantia;

		active.dtAquisTMSTMP = System.currentTimeMillis();
		active.dtFinalTMSTMP = System.currentTimeMillis();

		active.createdAt = new Date();
		active.updatedAt = new Date();

		return active;
	}

}
