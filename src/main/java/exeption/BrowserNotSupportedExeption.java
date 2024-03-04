package exeption;

public class BrowserNotSupportedExeption extends RuntimeException {
    public BrowserNotSupportedExeption(String browserName) {
        super(String.format("Browser %s not supported", browserName));
    }
}
