package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Departament;
import br.edu.ifs.rfid.apirfid.domain.dto.DepartamentDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.DepartamentRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IDepartamentRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IDepartamentService;

@CacheConfig(cacheNames = "occupation")
@Service
public class DepartamentService implements IDepartamentService {

	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private IDepartamentRepository departamentRepository;
	private DepartamentRepository departamentRepositoryCustom;

	@Autowired
	public DepartamentService(IDepartamentRepository departamentRepository,
			DepartamentRepository departamentRepositoryCustom) {
		this.departamentRepository = departamentRepository;
		this.departamentRepositoryCustom = departamentRepositoryCustom;
	}

	@Override
	public Departament createDepartament(DepartamentDto request) {
		try {

			Departament departament = new Departament();

			departament = departament.createDepartament(request.getSigla(), request.getNome(), request.getDescricao());

			this.departamentRepository.save(departament);

			return departament;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#departamentId")
	@Override
	public Departament getDepartamentById(String departamentId) {
		try {

			Optional<Departament> findResult = this.departamentRepository.findById(departamentId);

			if (!findResult.isPresent()) {
				throw new CustomException("Departament not found.", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<Departament> getAllDepartaments() {
		try {

			return this.departamentRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteDepartament(String id) {
		try {

			this.getDepartamentById(id);

			this.departamentRepository.deleteById(id);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Departament updateDepartament(String departamentId, DepartamentDto request) {
		try {

			this.getDepartamentById(departamentId);

			if (request.isEmpty())
				throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST);

			return departamentRepositoryCustom.updateDepartament(departamentId, request);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
