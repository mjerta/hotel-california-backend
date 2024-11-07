package nl.mpdev.hotel_california_backend.models.enums;

public enum Status {
  NOT_PICKED_UP("Not Picked Up"),
  IN_QUEUE("In Queue"),
  PREPARING_ORDER("Preparing Order"),
  ORDER_PREPARED("Order Prepared"),
  ORDER_DELIVERED("Order Delivered"),
  ORDER_PAYED("Order Payed");

  private final String status;

  Status(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
