package br.edu.ifs.rfid.apirfid.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Tag;

@Repository
public interface ITagRepository extends MongoRepository<Tag, String> {
	@Query("{'epc' : ?0}")
	Tag findByEpc(String epc);
}
