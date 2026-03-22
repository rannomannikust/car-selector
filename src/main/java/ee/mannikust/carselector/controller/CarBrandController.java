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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CarBrandController {
    private static final Logger log = LoggerFactory.getLogger(CarBrandController.class);
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
        if (!model.containsAttribute("userSelectionDto")) {
            model.addAttribute("userSelectionDto", new UserSelectionDto());
        }
        
        // Tagastab faili nime, mida Spring otsib kataloogist src/main/resources/templates/
        return "index";
    }

    @PostMapping("/save")
    public String saveSelection(@Valid @ModelAttribute("userSelectionDto") UserSelectionDto selection, 
                                BindingResult bindingResult, 
                                Model model,
                                RedirectAttributes redirectAttributes) {

        // rida lisatud debugimiseks        // :
        //System.out.println("KONTROLLER: Vormi andmed saabusid! Vigu leiti: " + bindingResult.getErrorCount());                            
        log.info("TEHNILINE: Alustan andmete salvestamist kasutajale: {} {}", selection.getFirstName(), selection.getLastName());

        if (bindingResult.hasErrors()) {
            // Kui on vigu, laeme automargid ja näitame vormi uuesti
            model.addAttribute("carBrands", carBrandService.getHierarchicalCarBrands());
            log.warn("AUDIT: Kasutaja sisestas vigased andmed: {}", bindingResult.getAllErrors());
            return "index";
        }
        
        carBrandService.saveUserSelection(selection);

        // Kui vigu pole, võime saata teate 
        redirectAttributes.addFlashAttribute("successMessage", "Andmed on edukalt kontrollitud ja vastu võetud!");
        log.info("AUDIT: Kasutaja valik edukalt salvestatud andmebaasi.");
        return "redirect:/";
    }

    
}