package ee.mannikust.carselector.controller;

import ee.mannikust.carselector.service.CarBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarBrandController {

    private final CarBrandService carBrandService;

    // Süstime teenuse (Service) kontrollerisse
    public CarBrandController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping("/")
    public String showIndexPage(Model model) {
        // Kutsume välja oma treppimise loogika ja lisame tulemuse Thymeleaf mudelisse
        model.addAttribute("carBrands", carBrandService.getHierarchicalCarBrands());
        
        // Tagastab faili nime, mida Spring otsib kataloogist src/main/resources/templates/
        return "index";
    }
}