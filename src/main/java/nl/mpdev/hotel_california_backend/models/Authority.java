package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Authorities")
public class Authority implements GrantedAuthority {
  @Id
  @Column(nullable = false)
  private String username;
  @Id
  @Column(nullable = false)
  private String authority;


}