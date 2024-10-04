package nl.mpdev.hotel_california_backend.dtos.meals;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.models.Ingredient;

import java.util.List;

@Builder
@Getter
public class MealCompleteRequestDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private List<IngredientCompleteRequestDto> ingredients;
}
