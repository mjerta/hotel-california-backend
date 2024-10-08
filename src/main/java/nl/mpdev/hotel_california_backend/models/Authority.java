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
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
  // Using both the username and authority as @id fields to create a composite primary key
  @Id
  @Column(nullable = false)
  private String username;
  @Id
  @Column(nullable = false)
  private String authority;


}