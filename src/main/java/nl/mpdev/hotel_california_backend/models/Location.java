package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;
import nl.mpdev.hotel_california_backend.models.enums.LocationType;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Locations")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer locationNumber;
  private Boolean isOccupied;
  @Enumerated(EnumType.STRING)
  private LocationType locationType;
  @OneToOne(mappedBy = "destination")
  private Order order;
}
