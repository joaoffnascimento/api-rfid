package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Occupation;
import br.edu.ifs.rfid.apirfid.domain.dto.OccupationDto;

public interface IOccupationService {

	public Occupation createOccupation(OccupationDto request);

	public Occupation getOccupationById(String occupationId);

	public List<Occupation> getAllOcuppations();

	public Boolean deleteOccupation(String id);

	public Occupation updateOccupation(String occupationId, OccupationDto request);
}
