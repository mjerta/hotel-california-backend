package nl.mpdev.hotel_california_backend.models.enums;

public enum LocationType {
  TABLE("table"),
  HOTEL_ROOM("Hotel Room"),
  SUN_LOUNGER("Sun Lounger");

  private final String locationType;

  LocationType(String locationType) {
    this.locationType = locationType;
  }

  public String getType() {
    return locationType;
  }
}