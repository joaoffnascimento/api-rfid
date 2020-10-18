package br.edu.ifs.rfid.apirfid.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Tag;

@Repository
public class TagRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Tag findByEpc(String epc) {

		Query query = new Query();

		query.addCriteria(Criteria.where("epc").is(epc));

		return mongoTemplate.findOne(query, Tag.class);
	}
	
	public Tag findTagByActiveId(String activeId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("activeId").is(activeId));

		return mongoTemplate.findOne(query, Tag.class);
	}
}
