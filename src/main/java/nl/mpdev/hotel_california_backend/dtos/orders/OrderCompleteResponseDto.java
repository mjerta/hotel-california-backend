package nl.mpdev.hotel_california_backend.dtos.orders;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.locations.LocationCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderCompleteResponseDto {
  private Integer id;
  private LocalDateTime orderDate;
  private UserCompleteResponseDto user;
  private List<MealCompleteResponseDto> meals;
  private List<DrinkCompleteResponseDto> drinks;
  private Status status;
  private LocationCompleteResponseDto destination;
}
