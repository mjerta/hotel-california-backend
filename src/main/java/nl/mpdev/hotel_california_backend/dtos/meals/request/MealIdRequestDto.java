package nl.mpdev.hotel_california_backend.dtos.meals.request;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class MealIdRequestDto {
  private Integer id;
}
