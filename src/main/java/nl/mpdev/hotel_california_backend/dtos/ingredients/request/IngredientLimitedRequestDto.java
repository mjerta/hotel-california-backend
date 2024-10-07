package nl.mpdev.hotel_california_backend.dtos.ingredients.request;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class IngredientLimitedRequestDto {
  private String name;
  private Integer mealId;
}
