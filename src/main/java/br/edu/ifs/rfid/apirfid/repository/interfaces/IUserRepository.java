package br.edu.ifs.rfid.apirfid.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

}
