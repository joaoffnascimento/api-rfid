package br.edu.ifs.rfid.apirfid.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Active;
import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveDto;

@Repository
public interface IActiveRepository extends MongoRepository<Active, String> {
}
