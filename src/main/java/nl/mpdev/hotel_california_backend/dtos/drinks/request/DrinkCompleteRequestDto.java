package nl.mpdev.hotel_california_backend.dtos.drinks.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class DrinkCompleteRequestDto {
  @NotBlank(message = "Name cannot be blank")
  private String name;
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
  @NotNull(message = "Price must be set")
  private Double price;
  @NotNull(message = "Is alcholic cannot be null")
  private Boolean isAlcoholic;
  @NotNull(message = "Size cannot be null")
  @Min(value = 1, message = "Size must be at least 1 character")
  private Integer size;
  @NotBlank(message = "Measurement cannot be blank")
  private String measurement;
}
