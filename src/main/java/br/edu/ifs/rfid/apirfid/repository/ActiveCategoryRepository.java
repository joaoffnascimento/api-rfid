package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.ActiveCategory;
import br.edu.ifs.rfid.apirfid.domain.Occupation;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveCategoryDto;
import br.edu.ifs.rfid.apirfid.domain.dto.OccupationDto;

@Repository
public class ActiveCategoryRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public ActiveCategory findActiveCategoryById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, ActiveCategory.class);
	}

	public ActiveCategory updateActiveCategory(String activeCategoryId, ActiveCategoryDto activeCategoryDto) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(activeCategoryId));

		Update update = new Update();

		if (activeCategoryDto.getSigla() != null)
			update.set("sigla", activeCategoryDto.getSigla());

		if (activeCategoryDto.getDescricao() != null)
			update.set("descricao", activeCategoryDto.getDescricao());

		if (activeCategoryDto.getTipo() != null)
			update.set("tipo", activeCategoryDto.getTipo());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, ActiveCategory.class);

		return findActiveCategoryById(activeCategoryId);

	}
}
