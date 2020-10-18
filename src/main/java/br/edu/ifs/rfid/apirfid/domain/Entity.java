package br.edu.ifs.rfid.apirfid.domain;

import java.lang.reflect.Field;
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

	public boolean isEmpty() {

		for (Field field : this.getClass().getDeclaredFields()) {
			try {

				field.setAccessible(true);

				if (field.get(this) != null) {
					return Boolean.FALSE;
				}

			} catch (Exception e) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
}
