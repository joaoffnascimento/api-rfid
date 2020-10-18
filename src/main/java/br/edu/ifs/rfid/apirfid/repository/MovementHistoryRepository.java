package br.edu.ifs.rfid.apirfid.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;

@Repository
public class MovementHistoryRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public MovementHistory getLastMovmentHistoryByActiveId(String activeId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("activeId").is(activeId))
				.with(Sort.by(Sort.Direction.DESC, "dataHoraMovimentacao"));

		List<MovementHistory> result = mongoTemplate.find(query, MovementHistory.class);

		if (result.isEmpty()) {
			return null;
		}

		return result.get(0);
	}
}
