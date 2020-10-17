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

import br.edu.ifs.rfid.apirfid.domain.Occupation;
import br.edu.ifs.rfid.apirfid.domain.dto.OccupationDto;
import br.edu.ifs.rfid.apirfid.service.OccupationService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/api/occupation")
public class OccupationController {

	@Autowired
	private OccupationService occupationService;

	@PostMapping
	public ResponseEntity<Response<Occupation>> createOccupation(@Valid @RequestBody OccupationDto request) {

		Response<Occupation> response = new Response<>(true);

		response.setData(occupationService.createOccupation(request));
		response.setStatusCode(HttpStatus.CREATED.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(OccupationController.class).createOccupation(request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Occupation>> getOccupationById(@PathVariable String id) {

		Response<Occupation> response = new Response<>(true);

		response.setData(occupationService.getOccupationById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(OccupationController.class).getOccupationById(id)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<List<Occupation>>> getAllOccupations(){
		Response<List<Occupation>> response = new Response<>(true);

		response.setData(occupationService.getAllOcuppations());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(OccupationController.class).getAllOccupations()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Response<Occupation>> updateOccupation(@PathVariable String id, @Valid @RequestBody OccupationDto request) {

		Response<Occupation> response = new Response<>(true);

		response.setData(occupationService.updateOccupation(id, request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(OccupationController.class).createOccupation(request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteOccupation(@PathVariable String id) {
		Response<Boolean> response = new Response<>(true);

		response.setData(occupationService.deleteOccupation(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OccupationController.class).deleteOccupation(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
