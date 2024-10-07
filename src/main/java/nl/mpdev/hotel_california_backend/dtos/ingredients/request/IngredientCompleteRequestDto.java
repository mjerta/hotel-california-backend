package nl.mpdev.hotel_california_backend.dtos.ingredients.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class IngredientCompleteRequestDto {
  @NotNull(message = "ID cannot be null")
  private Integer id;
  @NotNull(message = "Name cannot be null")
  @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
  private String name;
}
