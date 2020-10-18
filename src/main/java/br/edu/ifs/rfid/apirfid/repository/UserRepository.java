package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.User;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.shared.FnUtil;

@Repository
public class UserRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public User findUserByEmail(String email) {

		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(email));

		return mongoTemplate.findOne(query, User.class);
	}

	public Employee findEmployeeByEmail(String email) {

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

		Optional<Employee> findResult = Optional.ofNullable(findEmployeeByEmail(employeeDto.getEmail()));

		if (findResult.isPresent()) {
			throw new CustomException("Email already exists.", HttpStatus.NOT_FOUND);
		}

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(employeerId));

		Update update = new Update();

		if (employeeDto.getMatSiape() != null)
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

		// if (employeeDto.getPassword() != null)
		// update.set("password", FnUtil.bcryptEncode(employeeDto.getPassword()));

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Employee.class);

		return findEmployeerById(employeerId);
	}

	public Employee changePassword(UserDto userDto) {

		Query query = new Query();

		query.addCriteria(Criteria.where("email").is(userDto.getEmail()));

		Update update = new Update();

		if (userDto.getPassword() != null)
			update.set("password", FnUtil.bcryptEncode(userDto.getPassword()));

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Employee.class);

		return findEmployeeByEmail(userDto.getEmail());

	}
}
