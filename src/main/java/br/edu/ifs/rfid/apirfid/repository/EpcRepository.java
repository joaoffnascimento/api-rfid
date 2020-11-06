package br.edu.ifs.rfid.apirfid.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Epc;

@Repository
public class EpcRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Epc getLastEpc() {

		Query query = new Query();

		query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

		query.limit(1);

		return mongoTemplate.findOne(query, Epc.class);
	}
}
