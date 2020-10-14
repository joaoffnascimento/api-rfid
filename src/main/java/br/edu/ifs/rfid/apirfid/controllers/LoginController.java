package br.edu.ifs.rfid.apirfid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.User;
import br.edu.ifs.rfid.apirfid.domain.dto.UserDto;
import br.edu.ifs.rfid.apirfid.service.UserService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Response<User>> efetuarLogin(@Valid @RequestBody UserDto request) {

		Response<User> response = new Response<>(true);

		response.setData(userService.login(request));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
