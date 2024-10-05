package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Drinks")
//public class Drink extends MenuItem {
public class Drink {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private Boolean isAlcoholic;
  private Integer size;
  private String measurement;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
}
