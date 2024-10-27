package nl.mpdev.hotel_california_backend.dtos.meals.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class MealUpdateRequestDto {
  @NotBlank(message = "Name cannot be blank")
  private String name;
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  @NotNull(message = "Price must be set")
  private Double price;
  @Valid
  @NotNull(message = "Ingredient can not be null")
  private List<IngredientCompleteRequestDto> ingredients;
}
