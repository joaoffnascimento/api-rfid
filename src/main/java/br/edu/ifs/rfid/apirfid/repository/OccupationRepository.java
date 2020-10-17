package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Occupation;
import br.edu.ifs.rfid.apirfid.domain.dto.OccupationDto;

@Repository
public class OccupationRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Occupation findOccupationById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, Occupation.class);
	}

	public Occupation updateOccupation(String occupationId, OccupationDto occupation) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(occupationId));

		Update update = new Update();

		if (occupation.getFuncao() != null)
			update.set("funcao", occupation.getFuncao());

		if (occupation.getDescricao() != null)
			update.set("descricao", occupation.getDescricao());

		if (occupation.getDataInicioFuncao() != null)
			update.set("dataInicioFuncao", occupation.getDataInicioFuncao());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Occupation.class);

		return findOccupationById(occupationId);

	}
}
