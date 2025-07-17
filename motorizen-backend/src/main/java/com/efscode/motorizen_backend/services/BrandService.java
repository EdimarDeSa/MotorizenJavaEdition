package com.efscode.motorizen_backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.Utils.Validators;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.dtos.brand.BrandDTO;
import com.efscode.motorizen_backend.models.dtos.brand.BrandMapper;
import com.efscode.motorizen_backend.models.dtos.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.dtos.brand.NewBrandDTO;
import com.efscode.motorizen_backend.models.entitys.BrandEntity;
import com.efscode.motorizen_backend.repositorys.BrandRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepo;
  private final Validators validators;

  public List<BrandDTO> findAllBrands() {
    List<BrandEntity> brands = brandRepo.findByDeletedAtIsNull();

    if (brands.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    return BrandMapper.INSTANCE.entitiesToDtos(brands);
  }

  public List<BrandDTO> filterBrands(String filter) {
    List<BrandEntity> brands = brandRepo.findByNameContainingIgnoreCaseAndDeletedAtIsNull(filter);

    if (brands.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    return BrandMapper.INSTANCE.entitiesToDtos(brands);
  }

  public void createNewBrand(NewBrandDTO newBrand) {
    try {
      validators.validateNewBrand(BrandMapper.INSTANCE.newDtoToDto(newBrand));

      BrandEntity brand = createNewOrReactivateBrand(newBrand);

      brandRepo.save(brand);

    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);
    }
  }

  public BrandDTO updateBrand(Integer id, BrandUpdatesDTO brandUpdates) {
    validators.validateBrandUpdates(BrandMapper.INSTANCE.updatesDtoToDto(brandUpdates), id);

    BrandEntity brand = brandRepo.findByIdAndDeletedAtIsNull(id);

    BrandMapper.INSTANCE.updateEntityFromDto(brandUpdates, brand);

    brandRepo.save(brand);

    return BrandMapper.INSTANCE.entityToDto(brand);
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
      brand = BrandMapper.INSTANCE.newDtoToEntity(newBrand);
    }

    return brand;
  }
}
