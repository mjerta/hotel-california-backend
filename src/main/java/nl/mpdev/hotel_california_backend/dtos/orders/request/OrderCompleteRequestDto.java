package nl.mpdev.hotel_california_backend.dtos.orders.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.locations.request.LocationIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserIdRequestDto;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderCompleteRequestDto {
  @NotNull(message = "User must not be null")
  private UserIdRequestDto user;
//  @NotNull(message = "Meals can not be null")
  private List<MealIdRequestDto> meals;
//  @NotNull(message = "Ingredient can not be null")
  private List<DrinkIdRequestDto> drinks;
  private Status status;
  @NotNull(message = "Destination can not be bull")
  private LocationIdRequestDto destination;
}
