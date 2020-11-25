package br.edu.ifs.rfid.apirfid.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;

@Repository
public class TagRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Tag findTagById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return mongoTemplate.findOne(query, Tag.class);
	}

	public Tag findByEpc(String epc) {

		Query query = new Query();

		query.addCriteria(Criteria.where("epc").is(epc));

		return mongoTemplate.findOne(query, Tag.class);
	}

	public Tag findTagByActiveId(String activeId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("activeId").is(activeId));

		return mongoTemplate.findOne(query, Tag.class);
	}

	public List<Tag> getTagsWithoutActiveId() {

		Query query = new Query();

		query.addCriteria(Criteria.where("activeId").is(null));

		return mongoTemplate.find(query, Tag.class);
	}
	
	public Tag updateTag(String tagId, TagDto tagDto) {

		Optional<Tag> findResult = Optional.ofNullable(findTagById(tagId));

		if (!findResult.isPresent()) {
			throw new CustomException("Tag not found !", HttpStatus.NOT_FOUND);
		}

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(tagId));

		Update update = new Update();

		if (tagDto.getActiveId() != null)
			update.set("activeId", tagDto.getActiveId());

		if (tagDto.getEpc() != null)
			update.set("epc", tagDto.getEpc());

		if (tagDto.getTipo() != null)
			update.set("tipo", tagDto.getTipo());

		update.set("updatedAt", new Date());

		mongoTemplate.findAndModify(query, update, Tag.class);

		return findTagById(tagId);
	}
}
