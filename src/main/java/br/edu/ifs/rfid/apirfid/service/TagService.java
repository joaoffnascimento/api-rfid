package br.edu.ifs.rfid.apirfid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.TagRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.ITagRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.ITagService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@CacheConfig(cacheNames = "tag")
@Service
public class TagService implements ITagService {

	private ITagRepository tagRepository;
	private TagRepository tagCustomRepository;

	@Autowired
	public TagService(ITagRepository tagRepository, TagRepository tagCustomRepository) {
		this.tagRepository = tagRepository;
		this.tagCustomRepository = tagCustomRepository;
	}

	@Override
	public Tag createTag(TagDto request) {
		try {

			Tag tag = new Tag();

			tag = tag.createTag(request.getEpc(), request.getTipo());

			this.tagRepository.save(tag);

			return tag;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Tag getTagByEpc(String epc) {
		try {

			Tag tag = this.tagCustomRepository.findByEpc(epc);

			if (tag == null) {
				throw new CustomException(Constants.getTagNotFoundError(), HttpStatus.NOT_FOUND);
			}

			return tag;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Tag> getAllTags() {
		try {

			return this.tagRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
