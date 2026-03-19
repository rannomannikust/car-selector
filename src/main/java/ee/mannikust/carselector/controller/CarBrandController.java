package ee.mannikust.carselector.controller;

import ee.mannikust.carselector.service.CarBrandService;
import ee.mannikust.carselector.dto.UserSelectionDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        // Lisame tühja objekti, et vormil oleks midagi, millega end siduda
        if (!model.containsAttribute("userSelection")) {
            model.addAttribute("userSelection", new UserSelectionDto());
        }
        
        // Tagastab faili nime, mida Spring otsib kataloogist src/main/resources/templates/
        return "index";
    }

    @PostMapping("/save")
    public String saveSelection(@Valid @ModelAttribute("userSelection") UserSelectionDto selection, 
                                BindingResult bindingResult, 
                                Model model,
                                RedirectAttributes redirectAttributes) {

        // rida lisatud debugimiseks        // :
        //System.out.println("KONTROLLER: Vormi andmed saabusid! Vigu leiti: " + bindingResult.getErrorCount());                            
        
        if (bindingResult.hasErrors()) {
            // Kui on vigu, laeme automargid ja näitame vormi uuesti
            model.addAttribute("carBrands", carBrandService.getHierarchicalCarBrands());
            return "index";
        }

        //carBrandService.saveUserSelection(selection);

        // Kui vigu pole, võime saata teate 
        redirectAttributes.addFlashAttribute("successMessage", "Andmed on edukalt kontrollitud ja vastu võetud!");
        return "redirect:/";
    }

    
}