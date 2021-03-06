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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;
import br.edu.ifs.rfid.apirfid.service.ActiveService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/v1/active")
public class ActiveController {

	@Autowired
	private ActiveService activeService;

	@PostMapping
	public ResponseEntity<Response<Active>> createActive(@Valid @RequestBody ActiveDto request) {

		Response<Active> response = new Response<>(true);

		response.setData(activeService.createActive(request));
		response.setStatusCode(HttpStatus.CREATED.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).createActive(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Active>> getActiveById(@PathVariable String id) {

		Response<Active> response = new Response<>(true);

		response.setData(activeService.getActiveById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).getActiveById(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/patrimonio")
	public ResponseEntity<Response<List<Active>>> getActivesByPatrimonio(
			@RequestParam(name = "patrimonio") int patrimonio) {

		Response<List<Active>> response = new Response<>(true);

		response.setData(activeService.getActivesByPatrimonio(patrimonio));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).getActivesByPatrimonio(patrimonio))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping()
	public ResponseEntity<Response<List<Active>>> getAllActives() {

		Response<List<Active>> response = new Response<>(true);

		response.setData(activeService.getAllActives());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).getAllActives())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/departament/{departamentId}")
	public ResponseEntity<Response<List<Active>>> getActivesByDepartamentId(@PathVariable String departamentId) {

		Response<List<Active>> response = new Response<>(true);

		response.setData(activeService.getActivesByDepartamentId(departamentId));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).getActivesByDepartamentId(departamentId))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{activeId}")
	public ResponseEntity<Response<Boolean>> deleteActive(@PathVariable String activeId) {

		Response<Boolean> response = new Response<>(true);

		response.setData(activeService.deleteActive(activeId));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).deleteActive(activeId))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Response<Active>> updateActive(@PathVariable String id, @RequestBody ActiveDto activeDto) {
		Response<Active> response = new Response<>(true);

		response.setData(activeService.updateActive(id, activeDto));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveController.class).updateActive(id, activeDto)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
