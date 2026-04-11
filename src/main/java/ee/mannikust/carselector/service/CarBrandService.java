package ee.mannikust.carselector.service;

import ee.mannikust.carselector.dto.CarBrandDto;
import ee.mannikust.carselector.dto.UserSelectionDto;
import ee.mannikust.carselector.model.CarBrand;
import ee.mannikust.carselector.model.UserSelection;
import ee.mannikust.carselector.repository.CarBrandRepository;
import ee.mannikust.carselector.repository.UserSelectionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarBrandService {

  private final CarBrandRepository repository;

  private final UserSelectionRepository userSelectionRepository;

  public List<CarBrandDto> getHierarchicalCarBrands(Locale locale) {
    List<CarBrandDto> result = new ArrayList<>();
    List<CarBrand> mainBrands = repository.findByParentIsNullOrderByNameAsc();

    for (CarBrand mainBrand : mainBrands) {
      String translated = translateBrandName(mainBrand.getName(), locale);
      result.add(new CarBrandDto(mainBrand.getId(), translated, 0));
      addSubBrands(mainBrand.getId(), 1, result, locale);
    }

    return result;
  }

  private void addSubBrands(Long parentId, int stepLevel, List<CarBrandDto> result, Locale locale) {
    List<CarBrand> subBrands = repository.findByParentIdOrderByNameAsc(parentId);

    for (CarBrand subBrand : subBrands) {
      String translated = translateBrandName(subBrand.getName(), locale);

      result.add(new CarBrandDto(subBrand.getId(), translated, stepLevel));
      addSubBrands(subBrand.getId(), stepLevel + 1, result, locale);
    }
  }

  @Transactional
  public void saveUserSelection(UserSelectionDto dto) {
    UserSelection selection = new UserSelection();
    selection.setFirstName(dto.getFirstName());
    selection.setLastName(dto.getLastName());
    selection.setHasLicense(dto.isHasLicense());

    List<CarBrand> brands = repository.findAllById(dto.getSelectedCarBrandIds());
    selection.setSelectedBrands(brands);

    userSelectionRepository.save(selection);
  }

  private final MessageSource messageSource;

  private String translateBrandName(String name, Locale locale) {
    String key = "brand.name." + name.replace(" ", ".");
    return messageSource.getMessage(key, null, name, locale);
  }
}
