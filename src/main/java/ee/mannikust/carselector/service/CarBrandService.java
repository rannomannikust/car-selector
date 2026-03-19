package ee.mannikust.carselector.service;

import ee.mannikust.carselector.dto.CarBrandDto;
import ee.mannikust.carselector.model.CarBrand;
import ee.mannikust.carselector.repository.CarBrandRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarBrandService {

    private final CarBrandRepository repository;

    // Spring süstib (inject) meile automaatselt repositooriumi
    public CarBrandService(CarBrandRepository repository) {
        this.repository = repository;
    }

    /**
     * Tagastab kõik automargid ja mudelid ühes lamedas (flat) nimekirjas,
     * nimed on HTML tühikutega trepitud.
     */
    public List<CarBrandDto> getHierarchicalCarBrands() {
        List<CarBrandDto> result = new ArrayList<>();
        
        // 1. Otsime kõik peamised margid (parent on null), tähestikujärjekorras
        List<CarBrand> mainBrands = repository.findByParentIsNullOrderByNameAsc();

        for (CarBrand mainBrand : mainBrands) {
            // Lisame peamise margi nimekirja (ilma tühikuteta)
            result.add(new CarBrandDto(mainBrand.getId(), mainBrand.getName()));
            
            // 2. Otsime selle margi kõik alam-mudelid (ja nende alam-mudelid)
            addSubBrands(mainBrand.getId(), 1, result);
        }
        
        return result;
    }

    /**
     * Rekursiivne abimeetod, mis otsib vanema ID järgi mudelid ja alam-mudelid ja lisab neile õige arvu tühikuid
     */
    private void addSubBrands(Long parentId, int level, List<CarBrandDto> result) {
        List<CarBrand> subBrands = repository.findByParentIdOrderByNameAsc(parentId);

        for (CarBrand subBrand : subBrands) {
            // Tekitame HTML tühikud (non-breaking spaces) vastavalt hierarhia sügavusele
            String prefix = "&nbsp;".repeat(level);
            
            result.add(new CarBrandDto(subBrand.getId(), prefix + subBrand.getName()));
            
            // Juhuks, kui mudelil on omakorda alam-mudelid (nt 3 seeria -> 316), kutsume meetodit uuesti välja
            addSubBrands(subBrand.getId(), level + 1, result);
        }
    }
}
