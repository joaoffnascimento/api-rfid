package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IEmployeeRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IEmployeeService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@CacheConfig(cacheNames = "employee")
@Service
public class EmployeeService implements IEmployeeService {

	private IEmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(IEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee createEmployee(EmployeeDto request) {
		try {

			Employee employee = new Employee();

			employee = employee.create(request.getMatSiape(), request.getNome(), request.getProfissao(),
					request.getInicioExercicio(), request.getOccupationId(), request.getDepartamentId());

			this.employeeRepository.save(employee);

			return employee;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#employeeId")
	@Override
	public Employee getEmployeeById(String employeeId) {
		try {

			Optional<Employee> findResult = this.employeeRepository.findById(employeeId);

			if (!findResult.isPresent()) {
				throw new CustomException(Constants.getEmployeeNotFoundError(), HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<Employee> getAllEmployeers() {
		try {

			return this.employeeRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteEmployeeById(String employeeId) {
		try {

			this.getEmployeeById(employeeId);

			this.employeeRepository.deleteById(employeeId);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
