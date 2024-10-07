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
//  kept this commented because I still need to figure out how to handle an image
//  @NotNull(message = "Image cannot be null")
//  @Size(min = 1, message = "Image must contain data")
  private byte[] image;
  @NotNull(message = "is_alcholic cannot be null")
  private Boolean isAlcoholic;
  @Min(value = 1, message = "Size must be a positive integer")
  private Integer size;
  @NotBlank(message = "Measurement cannot be blank")
  private String measurement;
}
