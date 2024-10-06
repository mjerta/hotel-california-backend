package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Profiles")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String address;
  private Integer points;
//  @OneToOne(mappedBy = "profile")
//  private User user;

}
