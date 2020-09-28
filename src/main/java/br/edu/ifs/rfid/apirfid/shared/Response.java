package br.edu.ifs.rfid.apirfid.shared;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> extends RepresentationModel<Response<T>> {
	// link class atribute

	private Boolean r;
	private int statusCode;
	private T data;
	private Long timeStamp;

	public Response(Boolean r) {
		this.r = r;
		this.timeStamp = System.currentTimeMillis();
	}
}
