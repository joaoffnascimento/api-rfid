package br.edu.ifs.rfid.apirfid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;
import br.edu.ifs.rfid.apirfid.service.ActiveService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/active")
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
}
