package nl.mpdev.hotel_california_backend.dtos.meals.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class MealUpdateRequestDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private List<IngredientCompleteRequestDto> ingredients;
}
