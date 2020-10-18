package br.edu.ifs.rfid.apirfid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import br.edu.ifs.rfid.apirfid.service.EmployeeService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<Response<Employee>> efetuarLogin(@Valid @RequestBody UserDto request) {

		Response<Employee> response = new Response<>(true);

		response.setData(employeeService.login(request));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Response<Employee>> createEmployee(@Valid @RequestBody EmployeeDto request) {

		Response<Employee> response = new Response<>(true);

		response.setData(employeeService.createEmployee(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LoginController.class).createEmployee(request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<Response<Employee>> changePassword(@Valid @RequestBody UserDto request) {

		Response<Employee> response = new Response<>(true);

		response.setData(employeeService.changePassword(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LoginController.class).changePassword(request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
