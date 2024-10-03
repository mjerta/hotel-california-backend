package nl.mpdev.hotel_california_backend.dtos.meals;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MealCompleteRequestDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
}
