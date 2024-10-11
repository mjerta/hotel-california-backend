package nl.mpdev.hotel_california_backend.dtos.orders.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.locations.request.LocationIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealIdRequestDto;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderNewRequestDto {
  //The valition of meals and drinks are being set in the service layer
  private List<MealIdRequestDto> meals;
  private List<DrinkIdRequestDto> drinks;
  @NotNull(message = "Destination can not be null")
  private LocationIdRequestDto destination;
}
