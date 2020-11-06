package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.Epc;
import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.EpcRepository;
import br.edu.ifs.rfid.apirfid.repository.IEpcRepository;
import br.edu.ifs.rfid.apirfid.repository.TagRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.ITagRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.ITagService;

@CacheConfig(cacheNames = "tag")
@Service
public class TagService implements ITagService {

	private static final String TAG_NOT_FOUND_ERROR = "Tag not found!";
	private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error, please contact our support";
	
	private EpcRepository epcRepositoryCustom;
	private IEpcRepository epcRepository;
	private ITagRepository tagRepository;
	private TagRepository tagCustomRepository;

	@Autowired
	public TagService(ITagRepository tagRepository, TagRepository tagCustomRepository, IEpcRepository epcRepository, EpcRepository epcRepositoryCustom) {
		this.tagRepository = tagRepository;
		this.tagCustomRepository = tagCustomRepository;
		this.epcRepository = epcRepository;
		this.epcRepositoryCustom = epcRepositoryCustom;
	}

	@Override
	public Tag getTagById(String tagId) {
		try {

			Optional<Tag> findResult = this.tagRepository.findById(tagId);

			if (!findResult.isPresent()) {
				throw new CustomException("Tag not found.", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Tag createTag(TagDto request) {
		try {

			Optional<Tag> findResult = Optional.ofNullable(this.tagCustomRepository.findByEpc(request.getEpc()));

			if (findResult.isPresent()) {
				throw new CustomException("EPC already exists", HttpStatus.BAD_REQUEST);
			}

			Tag tag = new Tag();

			tag = tag.createTag(request.getEpc(), request.getTipo());

			this.tagRepository.save(tag);

			return tag;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Tag getTagByEpc(String epc) {
		try {

			Optional<Tag> findResult = Optional.ofNullable(this.tagCustomRepository.findByEpc(epc));

			if (!findResult.isPresent()) {
				throw new CustomException(TAG_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Tag> getAllTags() {
		try {

			return this.tagRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Tag getTagByActiveId(String activeId) {
		try {

			Optional<Tag> findResult = Optional.ofNullable(this.tagCustomRepository.findTagByActiveId(activeId));

			if (!findResult.isPresent()) {
				throw new CustomException(TAG_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Tag updateTag(String tagId, TagDto tagDto) {
		try {

			Optional<Tag> findResult = this.tagRepository.findById(tagId);

			if (!findResult.isPresent()) {
				throw new CustomException("Tag not found.", HttpStatus.NOT_FOUND);
			}

			return tagCustomRepository.updateTag(tagId, tagDto);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteTag(String tagId) {
		try {

			Optional<Tag> findResult = this.tagRepository.findById(tagId);

			if (!findResult.isPresent()) {
				throw new CustomException("Tag not found.", HttpStatus.NOT_FOUND);
			}

			Tag tag = findResult.get();

			if (tag.getActiveId() != (null)) {
				throw new CustomException("Tag associated to activeId.: " + tag.getActiveId(), HttpStatus.BAD_REQUEST);
			}

			this.tagRepository.deleteById(tagId);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void saveLastEpcRead(String epc) {
		try {
			
			Epc epcObj = new Epc();
			
			epcObj = epcObj.createEpc(epc);
			
			epcRepository.save(epcObj);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Epc getLastEpcRead() {
		try {
			
			return epcRepositoryCustom.getLastEpc();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
