package ee.mannikust.carselector.exception;

/**
 * Üldine erind CarSelector rakenduse äriloogika vigade jaoks.
 */
public class CarSelectorException extends RuntimeException {

    // Konstruktor ainult sõnumiga
    public CarSelectorException(String message) {
        super(message);
    }

    // Konstruktor sõnumi ja algpõhjusega (Exception Chaining - SonarQube kiidab!)
    public CarSelectorException(String message, Throwable cause) {
        super(message, cause);
    }
}