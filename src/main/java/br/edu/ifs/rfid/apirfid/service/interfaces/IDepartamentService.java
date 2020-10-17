package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Departament;
import br.edu.ifs.rfid.apirfid.domain.dto.DepartamentDto;

public interface IDepartamentService {
	public Departament createDepartament(DepartamentDto request);

	public Departament getDepartamentById(String departamentId);

	public List<Departament> getAllDepartaments();

	public Boolean deleteDepartament(String id);

	public Departament updateDepartament(String departamentId, DepartamentDto request);
}
