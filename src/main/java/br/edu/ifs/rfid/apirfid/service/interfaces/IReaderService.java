package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.dto.HostNameDto;
import br.edu.ifs.rfid.apirfid.domain.dto.ReaderDto;

public interface IReaderService {

	public Reader createReader(final ReaderDto reader);
	
	public Reader getReader(final String id);

	public List<Reader> getReaders();

	public Boolean deleteReader(final String id);
	
	public Boolean enableReader(HostNameDto request);
	
	public Boolean disableReader();
}
