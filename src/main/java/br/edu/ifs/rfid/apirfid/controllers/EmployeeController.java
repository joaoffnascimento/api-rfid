package br.edu.ifs.rfid.apirfid.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Employee;
import br.edu.ifs.rfid.apirfid.domain.dto.EmployeeDto;
import br.edu.ifs.rfid.apirfid.service.EmployeeService;
import br.edu.ifs.rfid.apirfid.shared.Constants;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<Response<Employee>> createEmployee(@Valid @RequestBody EmployeeDto request) {

		Response<Employee> response = new Response<>(true);

		response.setData(employeeService.createEmployee(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).createEmployee(request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteEmployee(@PathVariable String id) {
		Response<Boolean> response = new Response<>(true);

		response.setData(employeeService.deleteEmployeeById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployee(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<Employee>>> getAllEmployeers() {
		Response<List<Employee>> response = new Response<>(true);

		response.setData(employeeService.getAllEmployeers());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployeers())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Employee>> getEmployeeById(@PathVariable String id) {
		Response<Employee> response = new Response<>(true);

		response.setData(employeeService.getEmployeeById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployee(id))
				.withRel(Constants.getDelete()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}