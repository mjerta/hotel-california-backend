package nl.mpdev.hotel_california_backend.dtos.ingredients;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IngredientCompleteRequestDto {
  private Integer id;
  private String name;
}
