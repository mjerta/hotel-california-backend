package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Drinks")
public class Drink extends MenuItem {
  private Boolean isAlcoholic;
  private Integer size;
  private String measurement;
}
