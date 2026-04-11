package ee.mannikust.carselector.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
  // 1. Püüame kinni valideerimisvead (kui DTO-s on vigu)
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private static final String ERROR = "error";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String handleValidationErrors(MethodArgumentNotValidException ex, Model model) {
    log.error("Valideerimisviga", ex);
    return "index";
  }

  // 2. Püüame kinni meie oma äriloogika vead
  @ExceptionHandler(CarSelectorException.class)
  public String handleBusinessErrors(CarSelectorException ex, RedirectAttributes ra) {
    log.error("Äriloogika viga", ex);
    ra.addFlashAttribute(ERROR, ex.getMessage());
    return "redirect:/";
  }

  // 3. Püüame kinni ootamatud süsteemivead (et vältida S112 generic exceptionit)
  @ExceptionHandler(Exception.class)
  public String handleAllUncaughtErrors(Exception ex, Model model) {
    log.error("Süsteemne viga", ex);
    model.addAttribute(ERROR, "Vabandust, tekkis ootamatu süsteemne viga.");
    return ERROR; // Suunab error.html lehele
  }
}
