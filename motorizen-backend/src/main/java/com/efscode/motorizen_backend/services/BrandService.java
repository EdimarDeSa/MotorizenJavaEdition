package com.efscode.motorizen_backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.brand.BrandDTO;
import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.brand.BrandMapper;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.repositorys.BrandRepository;
import com.efscode.motorizen_backend.utils.Validators;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepo;
  private final Validators validators;
  private final BrandMapper brandMapper;

  public List<BrandDTO> findAllBrands() {
    List<BrandEntity> brands = brandRepo.findByDeletedAtIsNull();

    if (brands.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    return brandMapper.entitiesToDtos(brands);
  }

  public List<BrandDTO> filterBrands(String filter) {
    List<BrandEntity> brands = brandRepo.findByNameContainingIgnoreCaseAndDeletedAtIsNull(filter);

    if (brands.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    return brandMapper.entitiesToDtos(brands);
  }

  public void createBrand(NewBrandDTO newBrand) {
    try {
      validators.validateNewBrand(brandMapper.newDtoToDto(newBrand));

      BrandEntity brand = createNewOrReactivateBrand(newBrand);

      brandRepo.save(brand);

    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);
    }
  }

  public BrandDTO updateBrand(Integer id, BrandUpdatesDTO brandUpdates) {
    validators.validateBrandUpdates(brandMapper.updatesDtoToDto(brandUpdates), id);

    BrandEntity brand = brandRepo.findByIdAndDeletedAtIsNull(id);

    if (brand == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    brandMapper.updateEntityFromDto(brandUpdates, brand);

    brandRepo.save(brand);

    return brandMapper.entityToDto(brand);
  }

  public void deleteBrand(Integer id) {
    BrandEntity brand = brandRepo.findByIdAndDeletedAtIsNull(id);

    if (brand == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    brand.setDeletedAt(LocalDateTime.now());

    brandRepo.save(brand);
  }

  private BrandEntity createNewOrReactivateBrand(NewBrandDTO newBrand) {
    BrandEntity brand;

    if (brandRepo.existsByNameAndDeletedAtIsNotNull(newBrand.name())) {
      brand = brandRepo.findByName(newBrand.name());

      brand.setDeletedAt(null);

    } else {
      brand = brandMapper.newDtoToEntity(newBrand);
    }

    return brand;
  }
}
