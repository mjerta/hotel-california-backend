package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.awt.*;

@Entity
@Data
@Table(name = "Drinks")
public class Drink extends MenuItem {
  private Boolean isAlcoholic;
  private String Integer;
  private String containerType;
}
