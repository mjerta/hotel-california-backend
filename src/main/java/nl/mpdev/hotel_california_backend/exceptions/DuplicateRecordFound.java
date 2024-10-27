package nl.mpdev.hotel_california_backend.exceptions;

public class DuplicateRecordFound extends RuntimeException {
  public DuplicateRecordFound() {
    super("Duplicate record is found");
  }

  public DuplicateRecordFound(String message) {
    super(message);
  }
}