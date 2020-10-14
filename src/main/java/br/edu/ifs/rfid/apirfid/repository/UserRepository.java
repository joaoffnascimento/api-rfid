package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.User;

@Repository
public class UserRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public User findUserByEmail(String email) {

		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(email));

		return mongoTemplate.findOne(query, User.class);
	}

	public Boolean updateTokenUser(String userId, String token) {

		Query query = new Query();

		// query.addCriteria(Criteria.where("_id").is(new ObjectId(activeId)));
		query.addCriteria(Criteria.where("id").is(userId));

		Update update = new Update();

		update.set("token", token);

		update.set("updatedAt", new Date());

		User updateResult = mongoTemplate.findAndModify(query, update, User.class);

		if (updateResult == null) {

			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}
}
