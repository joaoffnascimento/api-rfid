package br.edu.ifs.rfid.apirfid.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import br.edu.ifs.rfid.apirfid.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends Entity {

	@Email(message = "Email is required")
	private String email;
	@NotBlank(message = "Password is required")
	private String password;
	
	private String token;
	
	public String getToken() {
		return token;
	}
}
