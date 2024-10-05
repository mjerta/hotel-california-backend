package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

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
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "user_id", referencedColumnName = "id")
//  private User user;
  @OneToMany(mappedBy = "order")
  private List<Meal> meals;
  @OneToMany(mappedBy = "order")
  private List<Drink> drinks;
  @Enumerated(EnumType.STRING)
  private Status status;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "destination_id", referencedColumnName = "id")
  private Location destination;
}
