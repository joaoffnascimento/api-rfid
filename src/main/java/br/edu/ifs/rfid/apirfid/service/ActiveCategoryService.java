package br.edu.ifs.rfid.apirfid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ifs.rfid.apirfid.domain.ActiveCategory;
import br.edu.ifs.rfid.apirfid.domain.dto.ActiveCategoryDto;
import br.edu.ifs.rfid.apirfid.exception.CustomException;
import br.edu.ifs.rfid.apirfid.repository.ActiveCategoryRepository;
import br.edu.ifs.rfid.apirfid.repository.interfaces.IActiveCategoryRepository;
import br.edu.ifs.rfid.apirfid.service.interfaces.IActiveCategoryService;
import br.edu.ifs.rfid.apirfid.shared.Constants;

@CacheConfig(cacheNames = "activeCategory")
@Service
public class ActiveCategoryService implements IActiveCategoryService {

	private IActiveCategoryRepository activeCategoryRepository;
	private ActiveCategoryRepository activeCategoryRepositoryCustom;

	@Autowired
	public ActiveCategoryService(IActiveCategoryRepository activeCategoryRepository,
			ActiveCategoryRepository activeCategoryRepositoryCustom) {
		this.activeCategoryRepository = activeCategoryRepository;
		this.activeCategoryRepositoryCustom = activeCategoryRepositoryCustom;
	}

	@Override
	public ActiveCategory createActiveCategory(ActiveCategoryDto request) {
		try {
			ActiveCategory activeCategory = new ActiveCategory();

			activeCategory = activeCategory.createActiveCategory(request.getSigla(), request.getDescricao(),
					request.getTipo());

			this.activeCategoryRepository.save(activeCategory);

			return activeCategory;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ActiveCategory getActiveCategoryById(String id) {
		try {

			Optional<ActiveCategory> findResult = this.activeCategoryRepository.findById(id);

			if (!findResult.isPresent()) {
				throw new CustomException("Active not found.", HttpStatus.NOT_FOUND);
			}

			return findResult.get();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size() < 10")
	@Override
	public List<ActiveCategory> getAllActiveCategory() {
		try {
			return this.activeCategoryRepository.findAll();

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ActiveCategory updateActiveCategory(String activeCategoryId, ActiveCategoryDto activeCategoryDto) {
		try {

			this.getActiveCategoryById(activeCategoryId);
			
			if (activeCategoryDto.isEmpty())
				throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST);

			return activeCategoryRepositoryCustom.updateActiveCategory(activeCategoryId, activeCategoryDto);

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean deleteActiveCategory(String activeCategoryId) {
		try {

			this.getActiveCategoryById(activeCategoryId);

			this.activeCategoryRepository.deleteById(activeCategoryId);

			return true;

		} catch (CustomException r) {
			throw r;
		} catch (Exception e) {
			throw new CustomException(Constants.getInternalServerErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
