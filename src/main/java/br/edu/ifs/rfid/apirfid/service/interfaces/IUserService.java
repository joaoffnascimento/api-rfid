package br.edu.ifs.rfid.apirfid.service.interfaces;

import br.edu.ifs.rfid.apirfid.domain.User;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;

public interface IUserService {

	public User getUserByEmail(String email);

	public User createUser(UserDto userDto);

	public User login(UserDto userDto);
}
