package br.edu.ifs.rfid.apirfid.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.user.ERole;
import br.edu.ifs.rfid.apirfid.domain.user.Role;

@Repository
public class RoleRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Role getRoleByName(ERole name) {

		Query query = new Query();

		query.addCriteria(Criteria.where("name").is(name));

		return mongoTemplate.findOne(query, Role.class);
	}
}
