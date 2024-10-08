package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  @Column(nullable = false)
  private boolean enabled = true;
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", referencedColumnName = "id")
  private Profile profile;
  @OneToMany(
    targetEntity = Authority.class,
    mappedBy = "username",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private Set<Authority> authorities = new HashSet<>();
//  @OneToMany(mappedBy = "user")
//  private List<Order> orders;
}
