package nl.mpdev.hotel_california_backend.dtos.orders;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.locations.LocationIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserIdRequestDto;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderCompleteRequestDto {
  private UserIdRequestDto user;
  private List<MealIdRequestDto> meals;
  private List<DrinkIdRequestDto> drinks;
  private Status status;
  private LocationIdRequestDto destination;
}
