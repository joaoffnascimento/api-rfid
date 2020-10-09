package br.edu.ifs.rfid.apirfid.service.interfaces;

import br.edu.ifs.rfid.apirfid.domain.ActiveCategory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveCategoryDto;

public interface IActiveCategoryService {

	public ActiveCategory createActiveCategory(final ActiveCategoryDto activeCategoryDto);

	public ActiveCategory getActiveCategoryById(final String id);

}
