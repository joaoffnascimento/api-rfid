package br.edu.ifs.rfid.apirfid.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	private ReaderService readerService;

	@GetMapping
	public ResponseEntity<List<Reader>> getReaders() {
		return ResponseEntity.status(HttpStatus.OK).body(readerService.getReaders());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reader> getReader(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(readerService.getReader(id));
	}

	@PostMapping
	public ResponseEntity<Reader> createReader(@Valid @RequestBody ReaderDto reader) {
		return ResponseEntity.status(HttpStatus.CREATED).body(readerService.createReader(reader));
	}

	@PutMapping("/{id}/reader-ip")
	public ResponseEntity<Reader> updateReaderIp(@Valid @PathVariable String id, @RequestBody ReaderDto reader) {
		return ResponseEntity.status(HttpStatus.OK).body(readerService.updateIp(id, reader));
	}

	@PutMapping("/{id}/reader-port")
	public ResponseEntity<Reader> updateReaderPort(@Valid @PathVariable String id, @RequestBody ReaderDto reader) {
		return ResponseEntity.status(HttpStatus.OK).body(readerService.updatePort(id, reader));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> excluirMateria(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteReader(id));
	}

}