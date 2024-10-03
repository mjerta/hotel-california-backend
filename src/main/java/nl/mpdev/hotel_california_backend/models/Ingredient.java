package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Ingredients")
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "meal_id")
  private Meal meal;
}
