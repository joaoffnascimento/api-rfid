package br.edu.ifs.rfid.apirfid.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Tag;

@Repository
public interface ITagRepository extends MongoRepository<Tag, String> {

	Optional<Tag> findByEpc(String epc);
}
