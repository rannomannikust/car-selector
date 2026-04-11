package ee.mannikust.carselector.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private static final String ERROR = "error";

  @ExceptionHandler(CarSelectorException.class)
  public String handleBusinessErrors(CarSelectorException ex, RedirectAttributes ra) {
    log.error("Äriloogika viga", ex);
    ra.addFlashAttribute(ERROR, ex.getMessage());
    return "redirect:/";
  }

  @ExceptionHandler(Exception.class)
  public String handleAllUncaughtErrors(Exception ex, Model model) {
    log.error("Süsteemne viga", ex);
    model.addAttribute(ERROR, "Vabandust, tekkis ootamatu süsteemne viga.");
    return ERROR; // Suunab error.html lehele
  }
}
