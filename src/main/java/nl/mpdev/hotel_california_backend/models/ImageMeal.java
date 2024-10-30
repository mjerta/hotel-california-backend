package nl.mpdev.hotel_california_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_meals")
public class ImageMeal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String contentType;
  private long size;
  @Lob
  private byte[] data;
}
