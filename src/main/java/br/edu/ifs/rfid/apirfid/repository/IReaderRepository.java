package br.edu.ifs.rfid.apirfid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Reader;

@Repository
public interface IReaderRepository extends MongoRepository<Reader, String>{

}
