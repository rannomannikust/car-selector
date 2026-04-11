package ee.mannikust.carselector.controller;

import ee.mannikust.carselector.dto.UserSelectionDto;
import ee.mannikust.carselector.service.CarBrandService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarBrandController {
  private static final Logger log = LoggerFactory.getLogger(CarBrandController.class);
  private final CarBrandService carBrandService;
  private final MessageSource messageSource;

  public CarBrandController(CarBrandService carBrandService, MessageSource messageSource) {
    this.carBrandService = carBrandService;
    this.messageSource = messageSource;
  }

  @GetMapping("/")
  public String showIndexPage(Model model, Locale locale) {
    model.addAttribute("carBrands", carBrandService.getHierarchicalCarBrands(locale));

    if (!model.containsAttribute("userSelectionDto")) {
      model.addAttribute("userSelectionDto", new UserSelectionDto());
    }

    return "index";
  }

  @PostMapping("/save")
  public String saveSelection(
      @Valid @ModelAttribute("userSelectionDto") UserSelectionDto selection,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    log.info(
        "TEHNILINE: Alustan andmete salvestamist kasutajale: {} {}",
        selection.getFirstName(),
        selection.getLastName());

    if (bindingResult.hasErrors()) {
      model.addAttribute("carBrands", carBrandService.getHierarchicalCarBrands(locale));
      log.warn("AUDIT: Kasutaja sisestas vigased andmed: {}", bindingResult.getAllErrors());
      return "index";
    }

    carBrandService.saveUserSelection(selection);
    String msg = messageSource.getMessage("success.message", null, locale);
    redirectAttributes.addFlashAttribute("successMessage", msg);
    log.info("AUDIT: Kasutaja valik edukalt salvestatud andmebaasi.");
    return "redirect:/";
  }
}
