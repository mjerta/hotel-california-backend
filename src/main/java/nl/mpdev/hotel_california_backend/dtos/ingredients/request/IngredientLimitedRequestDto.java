package nl.mpdev.hotel_california_backend.dtos.ingredients.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IngredientLimitedRequestDto {
  @NotBlank(message = "Name cannot be null")
  @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
  private String name;
}
