package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Occupation;
import br.edu.ifs.rfid.apirfid.domain.dto.OccupationDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.OccupationRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IOccupationRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IOccupationService;

@CacheConfig(cacheNames = "occupation")
@Service
public class OccupationService implements IOccupationService {

	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private IOccupationRepository occupationRepository;
	private OccupationRepository occupationRepoCustom;

	@Autowired
	public OccupationService(IOccupationRepository occupationRepository, OccupationRepository occupationRepoCustom) {
		this.occupationRepository = occupationRepository;
		this.occupationRepoCustom = occupationRepoCustom;
	}

	@Override
	public Occupation createOccupation(OccupationDto request) {
		try {

			Occupation occupation = new Occupation();

			occupation = occupation.createOccupation(request.getFuncao(), request.getDescricao(),
					request.getDataInicioFuncao());

			this.occupationRepository.save(occupation);

			return occupation;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#occupationId")
	@Override
	public Occupation getOccupationById(String occupationId) {
		try {

			Optional<Occupation> findResult = this.occupationRepository.findById(occupationId);

			if (!findResult.isPresent()) {
				throw new CustomException("Occupation not found.", HttpStatus.NOT_FOUND);
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
	public List<Occupation> getAllOcuppations() {
		try {

			return this.occupationRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteOccupation(String id) {
		try {

			this.getOccupationById(id);

			this.occupationRepository.deleteById(id);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Occupation updateOccupation(String occupationId, OccupationDto request) {
		try {

			this.getOccupationById(occupationId);

			if (request.isEmpty())
				throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST);

			return occupationRepoCustom.updateOccupation(occupationId, request);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
