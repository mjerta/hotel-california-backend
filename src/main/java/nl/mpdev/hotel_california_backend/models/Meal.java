package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meals")

//public class Meal extends MenuItem {
public class Meal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
  @OneToMany(mappedBy = "meal")
  List<Ingredient> ingredients;


//   public MealBuilder toBuilder() {
//     return Meal.builder()
//       .id(this.id)
//       .name(this.name)
//       .description(this.description)
//       .price(this.price)
//       .image(this.image)
//       .ingredients(this.ingredients);
//   }
}
