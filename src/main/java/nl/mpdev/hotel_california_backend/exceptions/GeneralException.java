package nl.mpdev.hotel_california_backend.exceptions;

public class GeneralException extends RuntimeException {
  public GeneralException() {
    super("Something went wrong");
  }
  public GeneralException(String message) {
    super(message);
  }
}
