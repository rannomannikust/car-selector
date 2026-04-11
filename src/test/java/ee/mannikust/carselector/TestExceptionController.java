package ee.mannikust.carselector;

import ee.mannikust.carselector.exception.CarSelectorException;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
class TestExceptionController {

  @GetMapping("/throw-business-error")
  public void throwBusinessError() {
    throw new CarSelectorException("Test business error");
  }

  @GetMapping("/throw-generic-error")
  public void throwGenericError() {
    throw new RuntimeException("Test generic error");
  }
}
