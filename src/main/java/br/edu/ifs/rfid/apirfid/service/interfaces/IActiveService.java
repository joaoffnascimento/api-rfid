package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.MovementHistory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;

public interface IActiveService {

	public Active createActive(final ActiveDto active);

	public Active getActiveByEpc(final String epc);

	public List<Active> getActivesByPatrimonio(final int patrimonio);
	
	public Boolean updateMovimentacao(int tipoMovimentacao, String activeId, String numPatrimonio);
	
	public Active getActiveById(String id);
	
	public Active getActiveByTagId(String id);
	
	public MovementHistory getLastMovmentHistoryByActiveId(String activeId);
	
	public List<Active> getAllActives();	

	public Boolean deleteActive(String activeId);
	
	public Active updateActive(String activeId, ActiveDto activeDto);
	
	public List<Active> getActivesByDepartamentId(String departamentId);
}
