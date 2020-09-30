package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;

public interface IActiveService {

	public Active createActive(final ActiveDto active);

	public Active getActiveByEpc(final String epc);

	public List<Active> getActivesByPatrimonio(final int patrimonio);

}
