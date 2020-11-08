package br.edu.ifs.rfid.apirfid.domain.user;

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
public class Role extends Entity {
	
	private ERole name;
	
	public Role createRole(ERole role) {
		Role roleObj = new Role();
		
		roleObj.name = role;
		
		return roleObj;
	}
}
