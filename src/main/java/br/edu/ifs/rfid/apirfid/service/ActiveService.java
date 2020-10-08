package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IActiveRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IMovementHistory;
import br.edu.ifs.rfid.apirfid.service.interfaces.IActiveService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@CacheConfig(cacheNames = "active")
@Service
public class ActiveService implements IActiveService {

	private IActiveRepository activeRepository;
	private IMovementHistory movementHistoryRepository;

	@Autowired
	public ActiveService(IActiveRepository activeRepository, IMovementHistory movementHistoryRepository) {
		this.activeRepository = activeRepository;
		this.movementHistoryRepository = movementHistoryRepository;
	}

	@Override
	public Active createActive(ActiveDto request) {
		try {
			Active active = new Active();

			active = active.createActive(request.getNumeroPatrimonio(), request.getNomeHost(), request.getMarca(),
					request.getModelo(), request.getDataAquisicao(), request.getDataFinalGarantia(),
					request.getHasGarantia());

			this.activeRepository.save(active);

			return active;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Active getActiveByEpc(String epc) {
		// TRASH
		try {

			Optional<Active> findResult = this.activeRepository.findById(epc);

			if (!findResult.isPresent()) {
				throw new CustomException(Constants.getReaderNotFoundError(), HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Active> getActivesByPatrimonio(int patrimonio) {
		// TRASH
		try {

			return this.activeRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean insertMovimentacao(int tipoMovimentacao, int numPatrimonio, String activeId) {
		try {

			MovementHistory movementHistory = new MovementHistory();

			movementHistory = movementHistory.createMovement(tipoMovimentacao, activeId, numPatrimonio);

			this.movementHistoryRepository.save(movementHistory);

			return Boolean.TRUE;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
