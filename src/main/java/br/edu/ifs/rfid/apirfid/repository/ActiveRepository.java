package br.edu.ifs.rfid.apirfid.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Active;

@Repository
public class ActiveRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Active getActiveByEpc(String epc) {

		Query query = new Query();

		query.addCriteria(Criteria.where("epc").is(epc));

		return mongoTemplate.findOne(query, Active.class);
	}

	public Active getActiveByTagId(String tagId) {

		Query query = new Query();

		// query.addCriteria(Criteria.where("_id").is(new ObjectId(activeId)));
		query.addCriteria(Criteria.where("tagId").is(tagId));

		return mongoTemplate.findOne(query, Active.class);
	}

	public Boolean updateLastMovimentacao(String activeId, int movimentacao) {

		Query query = new Query();

		// query.addCriteria(Criteria.where("_id").is(new ObjectId(activeId)));
		query.addCriteria(Criteria.where("id").is(activeId));

		Update update = new Update();

		update.set("lastMovimentacao", movimentacao);

		Active updateResult = mongoTemplate.findAndModify(query, update, Active.class);

		if (updateResult == null) {

			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}
	
	public List<Active> getActivesByPatrimonio(int patrimonio) {

		Query query = new Query();

		query.addCriteria(Criteria.where("numeroPatrimonio").is(patrimonio));

		return mongoTemplate.find(query, Active.class);
	}
}
