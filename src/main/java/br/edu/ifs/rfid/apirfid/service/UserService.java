package br.edu.ifs.rfid.apirfid.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.User;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.UserRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IUserRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IUserService;
import br.edu.ifs.rfid.apirfid.shared.Auth;
import br.edu.ifs.rfid.apirfid.shared.Constants;
import io.jsonwebtoken.Jwts;

@Service
public class UserService implements IUserService {

	private UserRepository userRepository;
	private IUserRepository defaultRepo;

	@Autowired
	public UserService(UserRepository userRepository, IUserRepository defaultRepo) {
		this.userRepository = userRepository;
		this.defaultRepo = defaultRepo;
	}

	@Override
	public User getUserByEmail(String email) {
		try {

			Optional<User> findResult = Optional.ofNullable(this.userRepository.findUserByEmail(email));

			if (!findResult.isPresent()) {
				throw new CustomException("User not found!", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public User createUser(UserDto request) {
		try {

			User result = getUserByEmail(request.getEmail());

			if (result == null) {
				throw new CustomException("User already exists", HttpStatus.BAD_REQUEST);
			}

			User user = new User();

			user = user.createUser(request.getEmail(), request.getPassword());

			this.defaultRepo.save(user);

			return user;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public User login(UserDto userDto) {
		try {

			String token = createToken(userDto);

			User result = getUserByEmail(userDto.getEmail());

			if (result == null) {
				throw new CustomException("User not found!", HttpStatus.NOT_FOUND);
			}

			userRepository.updateTokenUser(result.getId(), token);

			Optional<User> findResult = defaultRepo.findById(result.getId());

			if (!findResult.isPresent()) {
				throw new CustomException("User not found!", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String createToken(UserDto user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("usuarioSistema", user);
		return Auth.createToken(Jwts.claims(claims));
	}
}
