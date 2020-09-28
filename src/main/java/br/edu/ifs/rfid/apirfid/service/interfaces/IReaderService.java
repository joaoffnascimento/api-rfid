package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.dtoObjects.ReaderDto;

public interface IReaderService {

	public Reader createReader(final ReaderDto reader);

	public Reader updatePort(final String id, final ReaderDto reader);

	public Reader updateModel(final String id, final ReaderDto reader);

	public Reader updateBrand(final String id, final ReaderDto reader);

	public Reader updateIp(final String id, final ReaderDto reader);

	public Reader getReader(final String id);

	public List<Reader> getReaders();

	public Boolean deleteReader(final String id);
}
