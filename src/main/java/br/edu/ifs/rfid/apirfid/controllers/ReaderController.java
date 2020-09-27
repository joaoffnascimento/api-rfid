package br.edu.ifs.rfid.apirfid.controllers;

import java.util.Date;
import java.util.List;

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
import br.edu.ifs.rfid.apirfid.repository.IReaderRepository;

@RestController
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	private IReaderRepository readerRepository;

	@GetMapping
	public ResponseEntity<List<Reader>> getReaders() {
		return ResponseEntity.status(HttpStatus.OK).body(readerRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reader> getReader(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(readerRepository.findById(id).get());
	}

	@PostMapping
	public ResponseEntity<Boolean> createReader(@RequestBody Reader reader) {
		try {

			Reader createReader = new Reader(reader.getPort(), reader.getIp(), reader.getModel(), reader.getBrand());

			this.readerRepository.save(createReader);

			return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Boolean> updateReader(@PathVariable String id, @RequestBody Reader reader) {
		try {

			Reader oldReader = readerRepository.findById(id).get();

			oldReader.setBrand(reader.getBrand());
			oldReader.setIp(reader.getIp());
			oldReader.setModel(reader.getModel());
			oldReader.setPort(reader.getModel());
			oldReader.setUpdatedAt(new Date());

			this.readerRepository.save(oldReader);

			return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> excluirMateria(@PathVariable String id) {
		try {

			this.readerRepository.deleteById(id);

			return ResponseEntity.status(HttpStatus.OK).body(true);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

}