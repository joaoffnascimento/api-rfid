package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.Dto.ReaderDto;
import br.edu.ifs.rfid.apirfid.exception.ReaderException;
import br.edu.ifs.rfid.apirfid.repository.IReaderRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IReaderService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@Service
public class ReaderService implements IReaderService {

	private IReaderRepository readerRepository;
	private ModelMapper mapper;

	@Autowired
	public ReaderService(IReaderRepository readerRepository, ModelMapper mapper) {
		this.readerRepository = readerRepository;
		this.mapper = mapper;
	}

	@Override
	public Reader createReader(ReaderDto request) {
		try {

			/**
			 * With Mapper: ModelMapper mapper = new ModelMapper(); Reader reader =
			 * mapper.map(request, Reader.class);
			 * 
			 * Use this top map the complete object to another class
			 */

			Reader reader = new Reader();

			reader = reader.createReader(request.getPort(), request.getIp(), request.getModel(), request.getBrand());

			this.readerRepository.save(reader);

			return reader;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * -> Grouping same notations
	 * @Caching(evict = {
	 * 		@CacheEvict(value = "reader", key = "#id"),
	 * 		@CacheEvict(value = "another entity", key = "#id")
	 * })
	 */
	
	@CacheEvict(value = "reader", key = "#id")
	@Override
	public Reader updatePort(String id, ReaderDto request) {
		try {

			Reader reader = this.getReader(id);

			reader.setPort(request.getPort());

			this.readerRepository.save(reader);

			return reader;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CacheEvict(value = "reader", key = "#id")
	@Override
	public Reader updateModel(String id, ReaderDto request) {
		try {

			Reader reader = this.getReader(id);

			reader.setModel(request.getModel());

			this.readerRepository.save(reader);

			return reader;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CacheEvict(value = "reader", key = "#id")
	@Override
	public Reader updateBrand(String id, ReaderDto request) {
		try {

			Reader reader = this.getReader(id);

			reader.setBrand(request.getBrand());

			this.readerRepository.save(reader);

			return reader;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CacheEvict(value = "reader", key = "#id")
	@Override
	public Reader updateIp(String id, ReaderDto request) {
		try {

			Reader reader = this.getReader(id);

			reader.setIp(request.getIp());

			this.readerRepository.save(reader);

			return reader;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Reader> getReaders() {
		try {

			return this.readerRepository.findAll();

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Cacheable(value = "reader", key = "#id")
	@Override
	public Reader getReader(String id) {
		try {

			Optional<Reader> findResult = this.readerRepository.findById(id);

			if (!findResult.isPresent()) {
				throw new ReaderException(Constants.getReaderNotFoundError(), HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteReader(String id) {
		try {

			this.getReader(id);

			this.readerRepository.deleteById(id);

			return true;

		} catch (ReaderException r) {
			throw r;
		} catch (Exception e) {
			throw new ReaderException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
