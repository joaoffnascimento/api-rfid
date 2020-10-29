package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.dto.HostNameDto;
import br.edu.ifs.rfid.apirfid.domain.dto.ReaderDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.middleware.RfidMiddleware;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IReaderRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IReaderService;

@CacheConfig(cacheNames = "reader")
@Service
public class ReaderService implements IReaderService {

	private static final String READER_NOT_FOUND_ERROR = "Reader not found!";
	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private RfidMiddleware rfidMiddleware;
	private IReaderRepository readerRepository;

	@Autowired
	public ReaderService(IReaderRepository readerRepository, RfidMiddleware rfidMiddleware) {
		this.readerRepository = readerRepository;
		this.rfidMiddleware = rfidMiddleware;
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

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * -> Grouping same notations
	 * 
	 * @Caching(evict = {
	 * @CacheEvict(value = "reader", key = "#id"),
	 * @CacheEvict(value = "another entity", key = "#id") })
	 */

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<Reader> getReaders() {
		try {

			return this.readerRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#id")
	@Override
	public Reader getReader(String id) {
		try {

			Optional<Reader> findResult = this.readerRepository.findById(id);

			if (!findResult.isPresent()) {
				throw new CustomException(READER_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteReader(String id) {
		try {

			this.getReader(id);

			this.readerRepository.deleteById(id);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean enableReader(HostNameDto request) {
		try {

			return this.rfidMiddleware.run(request.getIp());

		} catch (CustomException e) {
			return Boolean.FALSE;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean disableReader() {
		try {

			return this.rfidMiddleware.stop();

		} catch (CustomException e) {
			return Boolean.FALSE;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
