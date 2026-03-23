package ee.mannikust.carselector.service;

import ee.mannikust.carselector.dto.CarBrandDto;
import ee.mannikust.carselector.dto.UserSelectionDto;
import ee.mannikust.carselector.model.CarBrand;
import ee.mannikust.carselector.model.UserSelection;
import ee.mannikust.carselector.repository.CarBrandRepository;
import ee.mannikust.carselector.repository.UserSelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CarBrandService {

    private final CarBrandRepository repository;
    private final UserSelectionRepository userSelectionRepository;


    /**
     * Tagastab kõik automargid ja mudelid ühes lamedas (flat) nimekirjas,
     * nimed on HTML tühikutega trepitud.
     */
    public List<CarBrandDto> getHierarchicalCarBrands(Locale locale) {
        List<CarBrandDto> result = new ArrayList<>();
        
        // 1. Otsime kõik peamised margid (parent on null), tähestikujärjekorras
        List<CarBrand> mainBrands = repository.findByParentIsNullOrderByNameAsc();

        for (CarBrand mainBrand : mainBrands) {
            String translated = translateBrandName(mainBrand.getName(), locale);
            // Lisame peamise margi nimekirja (ilma tühikuteta)
            //result.add(new CarBrandDto(mainBrand.getId(), mainBrand.getName()));
            result.add(new CarBrandDto(mainBrand.getId(), translated));            

            // 2. Otsime selle margi kõik alam-mudelid (ja nende alam-mudelid)
            addSubBrands(mainBrand.getId(), 1, result, locale);
        }
        
        return result;
    }

    /**
     * Rekursiivne abimeetod, mis otsib vanema ID järgi mudelid ja alam-mudelid ja lisab neile õige arvu tühikuid
     */
    private void addSubBrands(Long parentId, int level, List<CarBrandDto> result, Locale locale) {
        List<CarBrand> subBrands = repository.findByParentIdOrderByNameAsc(parentId);

        for (CarBrand subBrand : subBrands) {
            // Tekitame HTML tühikud (non-breaking spaces) vastavalt hierarhia sügavusele
            String translated = translateBrandName(subBrand.getName(), locale);
            String prefix = "&nbsp;".repeat(level);
            result.add(new CarBrandDto(subBrand.getId(), prefix + translated));

            
            // Juhuks, kui mudelil on omakorda alam-mudelid (nt 3 seeria -> 316), kutsume meetodit uuesti välja
            addSubBrands(subBrand.getId(), level + 1, result, locale);
        }
    }
    
    @Transactional
    public void saveUserSelection(UserSelectionDto dto) {
        UserSelection selection = new UserSelection();
        selection.setFirstName(dto.getFirstName());
        selection.setLastName(dto.getLastName());
        selection.setHasLicense(dto.isHasLicense());
        
        // Leiame andmebaasist kõik valitud margid nende ID-de järgi
        List<CarBrand> brands = repository.findAllById(dto.getSelectedCarBrandIds());
        selection.setSelectedBrands(brands);
        
        userSelectionRepository.save(selection);
    }    
    
    @Autowired
    private MessageSource messageSource;

    private String translateBrandName(String name, Locale locale) {
        String key = "brand.name." + name.replace(" ", ".");
        return messageSource.getMessage(key, null, name, locale);

    }

}
