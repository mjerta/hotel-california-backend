package nl.mpdev.hotel_california_backend.dtos.meals.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class MealUpdateRequestDto {
  private String name;
  private String description;
  private Double price;
  private byte[] image;
//  @Valid
//  @NotNull(message = "Ingredient can not be null")
  private List<IngredientCompleteRequestDto> ingredients;
}
