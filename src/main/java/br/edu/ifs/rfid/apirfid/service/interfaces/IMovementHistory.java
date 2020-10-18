package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.Date;
import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.MovementHistory;

public interface IMovementHistory {

	/**
	 * Relatórios
	 */

	public List<MovementHistory> getAllMovementHistory();

	public List<MovementHistory> getAllMovementHistoryByActiveId(String activeId);

	public List<MovementHistory> getAllMovementHistoryByNumPatrimonio(int numPatrimonio);

	public List<MovementHistory> getAllMovementHistoryByActiveIdDateRange(String activeId, Date dataInicio, Date dataFim);

	public List<MovementHistory> getAllMovementHistoryByNumPatrimonioDateRange(int numPatrimonio, Date dataInicio, Date dataFim);
}
