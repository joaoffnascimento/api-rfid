package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Departament;
import br.edu.ifs.rfid.apirfid.domain.dto.DepartamentDto;

@Repository
public class DepartamentRepository {
	@Autowired
	MongoTemplate mongoTemplate;

	public Departament findDepartamentById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, Departament.class);
	}

	public Departament updateDepartament(String departamentId, DepartamentDto departament) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(departamentId));

		Update update = new Update();

		if (departament.getNome() != null)
			update.set("nome", departament.getNome());

		if (departament.getDescricao() != null)
			update.set("descricao", departament.getDescricao());

		if (departament.getSigla() != null)
			update.set("sigla", departament.getSigla());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Departament.class);

		return findDepartamentById(departamentId);

	}
}
