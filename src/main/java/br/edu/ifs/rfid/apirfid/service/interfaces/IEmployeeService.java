package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;

public interface IEmployeeService {

	public Employee createEmployee(EmployeeDto employee);
	
	public Employee getEmployeeById(String employeeId);
	
	public List<Employee> getAllEmployeers();
	
	public Boolean deleteEmployeeById(String employeeId);
	
}
