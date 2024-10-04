package nl.mpdev.hotel_california_backend.exceptions;

public class RecordNotFoundException extends RuntimeException{
  public RecordNotFoundException() {
    super("Record not found");
  }

  public RecordNotFoundException(String message) {
    super(message);
  }
}
