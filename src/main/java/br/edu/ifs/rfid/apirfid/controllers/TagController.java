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

import br.edu.ifs.rfid.apirfid.domain.Epc;
import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;
import br.edu.ifs.rfid.apirfid.service.TagService;
import br.edu.ifs.rfid.apirfid.shared.Response;

@RestController
@RequestMapping("/v1/tag")
public class TagController {
	@Autowired
	private TagService tagService;

	@PostMapping
	public ResponseEntity<Response<Tag>> createTag(@Valid @RequestBody TagDto request) {

		Response<Tag> response = new Response<>(true);

		response.setData(tagService.createTag(request));
		response.setStatusCode(HttpStatus.CREATED.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).createTag(request))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/epc/{epc}")
	public ResponseEntity<Response<Tag>> getTagByEpc(@PathVariable String epc) {

		Response<Tag> response = new Response<>(true);

		response.setData(tagService.getTagByEpc(epc));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).getTagByEpc(epc))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<Tag>>> getAllTags() {

		Response<List<Tag>> response = new Response<>(true);

		response.setData(tagService.getAllTags());
		response.setStatusCode(HttpStatus.OK.value());

		response.add(
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).getAllTags()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/active/{activeId}")
	public ResponseEntity<Response<Tag>> getTabByActiveId(@PathVariable String activeId) {

		Response<Tag> response = new Response<>(true);

		response.setData(tagService.getTagByActiveId(activeId));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).getTabByActiveId(activeId)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{tagId}")
	public ResponseEntity<Response<Boolean>> deleteTag(@PathVariable String tagId){
		Response<Boolean> response = new Response<>(true);

		response.setData(tagService.deleteTag(tagId));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).deleteTag(tagId))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping("/{tagId}")
	public ResponseEntity<Response<Tag>> updateTag(@PathVariable String tagId, @RequestBody TagDto tagDto){
		Response<Tag> response = new Response<>(true);

		response.setData(tagService.updateTag(tagId, tagDto));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).updateTag(tagId, tagDto)).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/epc")
	public ResponseEntity<Response<Epc>> getLastEpcRead(){
		Response<Epc> response = new Response<>(true);
		
		response.setData(tagService.getLastEpcRead());
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).getLastEpcRead()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/without-active")
	public ResponseEntity<Response<List<Tag>>> getTagsWithoutActiveId(){
		Response<List<Tag>> response = new Response<>(true);
		
		response.setData(tagService.getTagsWithoutActiveId());
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).getTagsWithoutActiveId()).withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
