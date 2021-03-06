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

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.dto.HostNameDto;
import br.edu.ifs.rfid.apirfid.domain.dto.ReaderDto;
import br.edu.ifs.rfid.apirfid.service.ReaderService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/v1/reader")
public class ReaderController {
	@Autowired
	private ReaderService readerService;

	@GetMapping
	public ResponseEntity<Response<List<Reader>>> getReaders() {
		Response<List<Reader>> response = new Response<>(true);

		response.setData(readerService.getReaders());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).getReaders())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Reader>> getReader(@PathVariable String id) {
		Response<Reader> response = new Response<>(true);

		response.setData(readerService.getReader(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).getReader(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).deleteReader(id))
				.withRel("DELETE"));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Response<Reader>> createReader(@Valid @RequestBody ReaderDto request) {

		Response<Reader> response = new Response<>(true);

		response.setData(readerService.createReader(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).createReader(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteReader(@PathVariable String id) {
		Response<Boolean> response = new Response<>(true);

		response.setData(readerService.deleteReader(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).deleteReader(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/enable")
	public ResponseEntity<Response<Boolean>> enableReader(@RequestBody HostNameDto request) {

		Response<Boolean> response = new Response<>(true);

		response.setData(readerService.enableReader(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).enableReader(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/disable")
	public ResponseEntity<Response<Boolean>> disableReader() {
		Response<Boolean> response = new Response<>(true);

		response.setData(readerService.disableReader());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).disableReader())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}