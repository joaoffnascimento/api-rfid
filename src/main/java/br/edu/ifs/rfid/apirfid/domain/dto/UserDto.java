package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

	@Email(message = "Email is required")
	private String email;
	@NotBlank(message = "Password is required")
	private String password;

	private String oldPassword;

	private String token;
}
