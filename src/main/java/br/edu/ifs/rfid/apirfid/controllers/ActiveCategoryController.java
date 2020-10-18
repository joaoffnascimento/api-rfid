package br.edu.ifs.rfid.apirfid.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifs.rfid.apirfid.domain.ActiveCategory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveCategoryDto;
import br.edu.ifs.rfid.apirfid.service.ActiveCategoryService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/api/active-category")
public class ActiveCategoryController {

	@Autowired
	private ActiveCategoryService activeCategoryService;

	@PostMapping
	public ResponseEntity<Response<ActiveCategory>> createActiveCategory(
			@Valid @RequestBody ActiveCategoryDto request) {

		Response<ActiveCategory> response = new Response<>(true);

		response.setData(activeCategoryService.createActiveCategory(request));
		response.setStatusCode(HttpStatus.CREATED.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveCategoryController.class).createActiveCategory(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<ActiveCategory>> getActiveCategoryById(@PathVariable String id) {

		Response<ActiveCategory> response = new Response<>(true);

		response.setData(activeCategoryService.getActiveCategoryById(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveCategoryController.class).getActiveCategoryById(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<ActiveCategory>>> getAllActiveCategory() {

		Response<List<ActiveCategory>> response = new Response<>(true);

		response.setData(activeCategoryService.getAllActiveCategory());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveCategoryController.class).getAllActiveCategory())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Response<ActiveCategory>> updateActiveCategory(@PathVariable String id, @RequestBody ActiveCategoryDto activeCategoryDto) {

		Response<ActiveCategory> response = new Response<>(true);

		response.setData(activeCategoryService.updateActiveCategory(id, activeCategoryDto));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveCategoryController.class).updateActiveCategory(id, activeCategoryDto))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id")
	public ResponseEntity<Response<Boolean>> deleteActiveCategory(@PathVariable String id) {

		Response<Boolean> response = new Response<>(true);

		response.setData(activeCategoryService.deleteActiveCategory(id));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ActiveCategoryController.class).deleteActiveCategory(id))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
