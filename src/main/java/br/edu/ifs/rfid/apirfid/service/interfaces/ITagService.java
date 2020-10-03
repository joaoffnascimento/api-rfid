package br.edu.ifs.rfid.apirfid.service.interfaces;

import br.edu.ifs.rfid.apirfid.domain.Tag;
import br.edu.ifs.rfid.apirfid.domain.dto.TagDto;

public interface ITagService {

	public Tag createTag(TagDto tag);

	public Tag getTagByEpc(String epc);

}