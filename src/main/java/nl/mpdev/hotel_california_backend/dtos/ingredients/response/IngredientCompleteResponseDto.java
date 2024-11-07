package nl.mpdev.hotel_california_backend.dtos.ingredients.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class IngredientCompleteResponseDto {
  private Integer id;
  private String name;
}
