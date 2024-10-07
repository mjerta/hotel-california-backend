package nl.mpdev.hotel_california_backend.dtos.meals.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;

import java.util.List;

@Builder
@Getter
public class MealLimitedRequestDto {
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private List<IngredientLimitedRequestDto> ingredients;
}
