package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import javax.xml.stream.Location;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private LocalDateTime orderDate;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "order")
  private List<Meal> meals;
  @OneToMany(mappedBy = "order")
  private List<Drink> drinks;
//  private List<MenuItem> menuItems;
  private Status status;
//  private Location destination;

}
