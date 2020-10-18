package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.User;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;

@Repository
public class UserRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public User findUserByEmail(String email) {

		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(email));

		return mongoTemplate.findOne(query, User.class);
	}

	public Employee findEmployeerByEmail(String email) {

		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(email));

		return mongoTemplate.findOne(query, Employee.class);
	}

	public Boolean updateTokenUser(String userId, String token) {

		Query query = new Query();

		// query.addCriteria(Criteria.where("_id").is(new ObjectId(activeId)));
		query.addCriteria(Criteria.where("id").is(userId));

		Update update = new Update();

		update.set("token", token);

		update.set("updatedAt", new Date());

		Employee updateResult = mongoTemplate.findAndModify(query, update, Employee.class);

		if (updateResult == null) {

			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	public Employee findEmployeerById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, Employee.class);
	}

	public Employee updateEmployee(String employeerId, EmployeeDto employeeDto) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(employeerId));

		Update update = new Update();

		if (employeeDto.getMatSiape() != 0)
			update.set("matSiape", employeeDto.getMatSiape());

		if (employeeDto.getNome() != null)
			update.set("nome", employeeDto.getNome());

		if (employeeDto.getProfissao() != null)
			update.set("profissao", employeeDto.getProfissao());

		if (employeeDto.getInicioExercicio() != null)
			update.set("inicioExercicio", employeeDto.getInicioExercicio());

		if (employeeDto.getOccupationId() != null)
			update.set("occupationId", employeeDto.getOccupationId());

		if (employeeDto.getDepartamentId() != null)
			update.set("departamentId", employeeDto.getDepartamentId());

		if (employeeDto.getEmail() != null)
			update.set("email", employeeDto.getEmail());

		if (employeeDto.getPassword() != null)
			update.set("password", employeeDto.getPassword());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Employee.class);

		return findEmployeerById(employeerId);

	}
}
