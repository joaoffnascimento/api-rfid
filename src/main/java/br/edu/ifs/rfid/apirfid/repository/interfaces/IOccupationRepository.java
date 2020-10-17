package br.edu.ifs.rfid.apirfid.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Occupation;

@Repository
public interface IOccupationRepository extends MongoRepository<Occupation, String	> {

}
