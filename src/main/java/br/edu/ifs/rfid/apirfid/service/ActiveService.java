package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.ActiveRepository;
import br.edu.ifs.rfid.apirfid.repository.MovementHistoryRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IActiveRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IMovementHistory;
import br.edu.ifs.rfid.apirfid.service.interfaces.IActiveService;

@CacheConfig(cacheNames = "active")
@Service
public class ActiveService implements IActiveService {

	private static final String ACTIVE_NOT_FOUND_ERROR = "Active not found!";
	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private IActiveRepository activeRepository;
	private IMovementHistory movementHistoryRepository;
	private ActiveRepository activeCustomRepository;
	private MovementHistoryRepository movementCustomRepository;

	@Autowired
	public ActiveService(IActiveRepository activeRepository, IMovementHistory movementHistoryRepository,
			ActiveRepository activeCustomRepository, MovementHistoryRepository movementCustomRepository) {
		this.activeRepository = activeRepository;
		this.movementHistoryRepository = movementHistoryRepository;
		this.activeCustomRepository = activeCustomRepository;
		this.movementCustomRepository = movementCustomRepository;
	}

	@Override
	public Active createActive(ActiveDto request) {
		try {
			Active active = new Active();

			active = active.createActive(request.getNumeroPatrimonio(), request.getMarca(), request.getModelo(),
					request.getDataAquisicao(), request.getDataFinalGarantia(), request.getActiveCategoryId(),
					request.getDepartamentId(), request.getTagId());

			this.activeRepository.save(active);

			return active;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Active getActiveById(String id) {
		try {

			Optional<Active> findResult = this.activeRepository.findById(id);

			if (!findResult.isPresent()) {
				throw new CustomException("Active not found.", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Active getActiveByEpc(String epc) {
		try {

			Active active = this.activeCustomRepository.getActiveByEpc(epc);

			if (active == null) {
				throw new CustomException(ACTIVE_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
			}

			return active;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Active getActiveByTagId(String tagId) {
		try {

			return this.activeCustomRepository.getActiveByTagId(tagId);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<Active> getActivesByPatrimonio(int patrimonio) {
		try {

			return this.activeCustomRepository.getActivesByPatrimonio(patrimonio);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean updateMovimentacao(int tipoMovimentacao, String activeId, String numPatrimonio) {
		try {

			MovementHistory movementHistory = new MovementHistory();

			movementHistory = movementHistory.createMovement(tipoMovimentacao, activeId, numPatrimonio);

			this.movementHistoryRepository.save(movementHistory);

			return activeCustomRepository.updateLastMovimentacao(activeId, tipoMovimentacao);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public MovementHistory getLastMovmentHistoryByActiveId(String activeId) {
		try {

			return movementCustomRepository.getLastMovmentHistoryByActiveId(activeId);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<Active> getAllActives() {
		try {

			return this.activeRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteActive(String activeId) {
		try {

			this.getActiveById(activeId);

			this.activeRepository.deleteById(activeId);

			return Boolean.TRUE;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Active updateActive(String activeId, ActiveDto activeDto) {
		try {

			this.getActiveById(activeId);

			if (activeDto.isEmpty())
				throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST);

			return activeCustomRepository.updateActive(activeId, activeDto);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
