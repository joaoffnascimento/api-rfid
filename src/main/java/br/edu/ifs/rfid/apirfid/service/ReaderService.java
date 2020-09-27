package br.edu.ifs.rfid.apirfid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.repository.IReaderRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IReaderService;

@Service
public class ReaderService implements IReaderService {

	@Autowired
	private IReaderRepository readerRepository;

	@Override
	public Reader createReader(Reader request) {
		try {

			Reader reader = new Reader();

			reader = reader.createReader(request.getPort(), request.getIp(), request.getModel(), request.getBrand());

			this.readerRepository.save(reader);

			return reader;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Reader updatePort(String id, Reader request) {
		try {

			if (request.getPort().isEmpty() || request.getPort().isBlank()) {
				return null;
			}

			Reader reader = this.readerRepository.findById(id).get();

			reader.setPort(request.getPort());

			this.readerRepository.save(reader);

			return reader;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Reader updateModel(String id, Reader request) {
		try {

			if (request.getModel().isEmpty() || request.getModel().isBlank()) {
				return null;
			}

			Reader reader = this.readerRepository.findById(id).get();

			reader.setModel(request.getModel());

			this.readerRepository.save(reader);

			return reader;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Reader updateBrand(String id, Reader request) {
		try {

			if (request.getBrand().isEmpty() || request.getBrand().isBlank()) {
				return null;
			}

			Reader reader = this.readerRepository.findById(id).get();

			reader.setBrand(request.getBrand());

			this.readerRepository.save(reader);

			return reader;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Reader updateIp(String id, Reader request) {
		try {

			if (request.getIp().isEmpty() || request.getIp().isBlank()) {
				return null;
			}

			Reader reader = this.readerRepository.findById(id).get();

			reader.setIp(request.getIp());

			this.readerRepository.save(reader);

			return reader;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Reader> getReaders() {
		try {

			return this.readerRepository.findAll();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Reader getReader(String id) {
		try {

			Optional<Reader> materiaOptional = this.readerRepository.findById(id);

			if (materiaOptional.isPresent()) {
				return materiaOptional.get();
			}

			return null;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean deleteReader(String id) {
		try {

			this.readerRepository.deleteById(id);

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}
