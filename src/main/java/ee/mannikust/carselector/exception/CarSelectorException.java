package ee.mannikust.carselector.exception;

public class CarSelectorException extends RuntimeException {

  public CarSelectorException(String message) {
    super(message);
  }

  public CarSelectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
