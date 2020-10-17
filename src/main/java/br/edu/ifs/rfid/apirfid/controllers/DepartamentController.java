package br.edu.ifs.rfid.apirfid.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Departament;
import br.edu.ifs.rfid.apirfid.domain.dto.DepartamentDto;
import br.edu.ifs.rfid.apirfid.service.DepartamentService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/api/departament")
public class DepartamentController {

	@Autowired
	private DepartamentService departamentService;

	@PostMapping
	public ResponseEntity<Response<Departament>> createDepartament(@Valid @RequestBody DepartamentDto request) {

		Response<Departament> response = new Response<>(true);

		response.setData(departamentService.createDepartament(request));
		response.setStatusCode(HttpStatus.CREATED.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DepartamentController.class).createDepartament(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Departament>> getDepartamentById(@PathVariable String id) {

		Response<Departament> response = new Response<>(true);

		response.setData(departamentService.getDepartamentById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DepartamentController.class).getDepartamentById(id)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<Departament>>> getAllDepartaments() {
		Response<List<Departament>> response = new Response<>(true);

		response.setData(departamentService.getAllDepartaments());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DepartamentController.class).getAllDepartaments()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Response<Departament>> updateDepartament(@PathVariable String id,
			@Valid @RequestBody DepartamentDto request) {

		Response<Departament> response = new Response<>(true);

		response.setData(departamentService.updateDepartament(id, request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DepartamentController.class).updateDepartament(id, request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteDepartament(@PathVariable String id) {
		Response<Boolean> response = new Response<>(true);

		response.setData(departamentService.deleteDepartament(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DepartamentController.class).deleteDepartament(id)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
