package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Reader;

public interface IReaderService {

	public Reader createReader(final Reader reader);

	public Reader updatePort(final String id, final Reader reader);

	public Reader updateModel(final String id, final Reader reader);

	public Reader updateBrand(final String id, final Reader reader);

	public Reader updateIp(final String id, final Reader reader);

	public Reader getReader(final String id);

	public List<Reader> getReaders();

	public Boolean deleteReader(final String id);
}
