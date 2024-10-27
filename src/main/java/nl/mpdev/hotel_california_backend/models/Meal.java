package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meals")

public class Meal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String name;
  private String description;
  private Double price;
  @OneToOne(cascade = CascadeType.ALL)
  private ImageMeal image;
  @ManyToMany(mappedBy = "meals")
  private List<Order> orders = new ArrayList<>();
  @OneToMany(mappedBy = "meal", cascade = CascadeType.REMOVE , orphanRemoval = true)
  List<Ingredient> ingredients;
}
