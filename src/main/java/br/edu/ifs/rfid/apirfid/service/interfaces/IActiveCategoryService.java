package br.edu.ifs.rfid.apirfid.service.interfaces;

import java.util.List;

import br.edu.ifs.rfid.apirfid.domain.ActiveCategory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveCategoryDto;

public interface IActiveCategoryService {

	public ActiveCategory createActiveCategory(final ActiveCategoryDto activeCategoryDto);

	public ActiveCategory getActiveCategoryById(final String id);

	public List<ActiveCategory> getAllActiveCategory();

	public ActiveCategory updateActiveCategory(String activeCategoryId, ActiveCategoryDto activeCategoryDto);

	public Boolean deleteActiveCategory(String activeCategoryId);

}
