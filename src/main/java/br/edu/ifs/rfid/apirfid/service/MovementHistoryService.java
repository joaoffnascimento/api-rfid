package br.edu.ifs.rfid.apirfid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IMovementHistory;

@CacheConfig(cacheNames = "movementhistory")
@Service
public class MovementHistoryService {

	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";

	private IMovementHistory movementHistory;

	@Autowired
	public MovementHistoryService(IMovementHistory movementHistory) {
		this.movementHistory = movementHistory;
	}

	@CachePut(unless = "#result.size() < 10")
	public List<MovementHistory> getAllHistory() {
		try {

			return this.movementHistory.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
