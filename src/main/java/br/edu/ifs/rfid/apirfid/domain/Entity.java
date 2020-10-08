package br.edu.ifs.rfid.apirfid.domain;

import java.util.Date;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {

	@Id
	private String id;
	private Date createdAt;
	private Date updatedAt;

	public Entity() {

		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

}
