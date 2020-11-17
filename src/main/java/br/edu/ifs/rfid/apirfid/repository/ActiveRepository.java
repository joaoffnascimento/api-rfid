package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;

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
	
	public List<Active> getActivesByDepartamentId(String departamentId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("departamentId").is(departamentId));

		return mongoTemplate.find(query, Active.class);
	}

	public Active findActiveById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, Active.class);
	}

	public Active updateActive(String activeId, ActiveDto activeDto) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(activeId));

		Update update = new Update();

		if (activeDto.getNumeroPatrimonio() != null)
			update.set("numeroPatrimonio", activeDto.getNumeroPatrimonio());

		if (activeDto.getActiveCategoryId() != null)
			update.set("activeCategoryId", activeDto.getActiveCategoryId());
		
		if (activeDto.getDepartamentId() != null)
			update.set("departamentId", activeDto.getDepartamentId());

		if (activeDto.getTagId() != null)
			update.set("tagId", activeDto.getTagId());

		if (activeDto.getMarca() != null)
			update.set("marca", activeDto.getMarca());

		if (activeDto.getModelo() != null)
			update.set("modelo", activeDto.getModelo());

		if (activeDto.getDataAquisicao() != null)
			update.set("dataAquisicao", activeDto.getDataAquisicao());

		if (activeDto.getDataFinalGarantia() != null)
			update.set("dataFinalGarantia", activeDto.getDataFinalGarantia());

		if (activeDto.getDtAquisTMSTMP() != 0)
			update.set("dtAquisTMSTMP", activeDto.getDtAquisTMSTMP());

		if (activeDto.getDtFinalTMSTMP() != 0)
			update.set("dtFinalTMSTMP", activeDto.getDtFinalTMSTMP());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Active.class);

		return findActiveById(activeId);

	}
}
