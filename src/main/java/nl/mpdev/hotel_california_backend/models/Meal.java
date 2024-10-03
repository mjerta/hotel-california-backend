package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "meals")
@Builder
@Getter

//public class Meal extends MenuItem {
public class Meal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  private Order order;
  @OneToMany(mappedBy = "meal")
  List<Ingredient> ingredients;

  public Meal() {

  }
}
