package nl.mpdev.hotel_california_backend.dtos.meals.request;

import jakarta.persistence.Lob;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
public class MealLimitedRequestDto {
  @NotBlank(message = "Name cannot be blank")
  @Size(min = 4, max = 20, message = "Name must be between 4 and 20 characters")
  private String name;
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  @NotNull(message = "Price must be set")
  private Double price;
  @NotNull(message = "Image cannot be null")
  private MultipartFile image;
  @Valid
  @NotNull(message = "Ingredient can not be null")
  private List<IngredientLimitedRequestDto> ingredients;
}
