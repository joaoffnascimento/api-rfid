package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Reader;
import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.ITagRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.ITagService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@CacheConfig(cacheNames = "tag")
@Service
public class TagService implements ITagService {

	private ITagRepository tagRepository;

	@Autowired
	public TagService(ITagRepository tagRepository) {
		this.tagRepository = tagRepository;
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
			
			Optional<Tag> findResult = this.tagRepository.findByEpc(epc);

			if (!findResult.isPresent()) {
				throw new CustomException(Constants.getReaderNotFoundError(), HttpStatus.NOT_FOUND);
			}

			return findResult.get();

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
