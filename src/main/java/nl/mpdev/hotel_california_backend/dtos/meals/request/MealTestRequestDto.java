package nl.mpdev.hotel_california_backend.dtos.meals.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter

public class MealTestRequestDto {
  @NotBlank(message = "Name cannot be blank")
  private String name;
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  @NotNull(message = "Price must be set")
  private Double price;
  private List<IngredientLimitedRequestDto> ingredients;
  private MultipartFile image;
}
