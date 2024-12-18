package nl.mpdev.hotel_california_backend.dtos.orders.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealIdRequestDto;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderUpdateRequestDto {
  private List<MealIdRequestDto> meals;
  private List<DrinkIdRequestDto> drinks;
  @NotNull(message = "Status must not be null")
  private Status status;
}
