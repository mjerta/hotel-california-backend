package nl.mpdev.hotel_california_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private LocalDateTime orderDate;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "username")
  @JsonBackReference
  private User user;
  //  @OneToMany(mappedBy = "order")
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "orders_meals",
    // By default the name gets the of the owners class name + _id
    joinColumns = @JoinColumn(name = "order_id"),
    // Also this column name ar by default the name of Field name + _id
    inverseJoinColumns = @JoinColumn(name = "meal_id")
  )
  private List<Meal> meals = new ArrayList<>();
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "orders_drinks",
    // By default the name gets the of the owners class name + _id
    joinColumns = @JoinColumn(name = "order_id"),
    // Also this column name ar by default the name of Field name + _id
    inverseJoinColumns = @JoinColumn(name = "drink_id")
  )
  private List<Drink> drinks;
  @Enumerated(EnumType.STRING)
  private Status status;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "destination_id", referencedColumnName = "id")
  private Location destination;
  private String orderReference;

}
