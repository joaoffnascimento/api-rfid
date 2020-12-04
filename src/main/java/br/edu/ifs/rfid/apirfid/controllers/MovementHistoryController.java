package br.edu.ifs.rfid.apirfid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.service.MovementHistoryService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/v1/history")
public class MovementHistoryController {

	@Autowired
	private MovementHistoryService movementHistoryService;

	@GetMapping
	public ResponseEntity<Response<List<MovementHistory>>> getAllHistory() {

		Response<List<MovementHistory>> response = new Response<>(true);

		response.setData(movementHistoryService.getAllHistory());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MovementHistoryController.class).getAllHistory()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
