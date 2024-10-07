package nl.mpdev.hotel_california_backend.dtos.meals.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.response.IngredientCompleteResponseDto;

import java.util.List;

@Builder
@Getter
public class MealCompleteResponseDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private List<IngredientCompleteResponseDto> ingredients;
}
