package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import br.edu.ifs.rfid.apirfid.domain.user.Employee;

public interface IEmployeeService {

	public Employee createEmployee(EmployeeDto employee);

	public Employee getEmployeeById(String employeeId);

	public List<Employee> getAllEmployeers();

	public Boolean deleteEmployeeById(String employeeId);

	public Employee login(UserDto userDto);

	public Employee updateEmployee(String employeeId, EmployeeDto employeeDto);

	public Employee changePassword(UserDto employeeDto);
	
	public Employee getEmployeeByEmail(String employeeEmail);
}
