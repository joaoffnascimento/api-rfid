package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementHistory {

	private ObjectId id;
	private Date dataHoraMovimentacao;
	private int tipoMovimentacao;

	private Active active;
}
