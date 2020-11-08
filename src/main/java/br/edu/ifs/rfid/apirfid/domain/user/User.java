package br.edu.ifs.rfid.apirfid.domain.user;

import java.util.List;

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
public class User extends Entity {

	private String email;
	private String password;
	private String token;
	private List<Role> roles;

	public User createUser(String email, String password) {
		User user = new User();

		user.email = email;
		user.password = password;

		return user;
	}
}
