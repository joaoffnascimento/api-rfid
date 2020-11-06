package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;

public interface ITagService {
	
	public Tag getTagById(String id);

	public Tag createTag(TagDto tag);

	public Tag getTagByEpc(String epc);
	
	public Tag getTagByActiveId(String activeId);

	public List<Tag> getAllTags();
	
	public Boolean deleteTag(String tagId);
	
	public Tag updateTag(String tagId, TagDto tagDto);
}
