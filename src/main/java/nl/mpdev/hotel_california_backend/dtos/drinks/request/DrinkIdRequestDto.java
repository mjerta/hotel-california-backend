package nl.mpdev.hotel_california_backend.dtos.drinks.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrinkIdRequestDto {
  @NotNull(message = "ID cannot be null")
  private Integer id;
}
