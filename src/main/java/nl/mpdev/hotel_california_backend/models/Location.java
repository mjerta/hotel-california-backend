package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import nl.mpdev.hotel_california_backend.models.enums.LocationType;

@Data
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
}
