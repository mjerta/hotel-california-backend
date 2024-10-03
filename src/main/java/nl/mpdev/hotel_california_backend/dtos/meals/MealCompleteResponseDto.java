package nl.mpdev.hotel_california_backend.dtos.meals;

import lombok.Builder;

@Builder
public class MealCompleteResponseDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
}
