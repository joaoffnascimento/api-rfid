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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.Dto.ReaderDto;
import br.edu.ifs.rfid.apirfid.service.ReaderService;
import br.edu.ifs.rfid.apirfid.shared.Constants;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/reader")
public class ReaderController {
	@Autowired
	private ReaderService readerService;

	@GetMapping
	public ResponseEntity<Response<List<Reader>>> getReaders() {
		Response<List<Reader>> response = new Response<>();

		response.setData(readerService.getReaders());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).getReaders())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Reader>> getReader(@PathVariable String id) {
		Response<Reader> response = new Response<>();

		response.setData(readerService.getReader(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).getReader(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).deleteReader(id))
				.withRel(Constants.getDelete()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Response<Reader>> createReader(@Valid @RequestBody ReaderDto request) {

		Response<Reader> response = new Response<>();

		response.setData(readerService.createReader(request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).createReader(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}/reader-ip")
	public ResponseEntity<Response<Reader>> updateReaderIp(@Valid @PathVariable String id,
			@RequestBody ReaderDto request) {
		Response<Reader> response = new Response<>();

		response.setData(readerService.updateIp(id, request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).updateReaderIp(id, request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{id}/reader-port")
	public ResponseEntity<Response<Reader>> updateReaderPort(@Valid @PathVariable String id, @RequestBody ReaderDto request) {
		Response<Reader> response = new Response<>();

		response.setData(readerService.updatePort(id, request));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).updateReaderPort(id, request)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteReader(@PathVariable String id) {
		Response<Boolean> response = new Response<>();

		response.setData(readerService.deleteReader(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ReaderController.class).deleteReader(id)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}