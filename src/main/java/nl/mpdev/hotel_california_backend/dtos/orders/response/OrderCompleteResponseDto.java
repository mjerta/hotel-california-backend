package nl.mpdev.hotel_california_backend.dtos.orders.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.response.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.locations.response.LocationCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.meals.response.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserLimitedResponseDto;
import nl.mpdev.hotel_california_backend.models.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderCompleteResponseDto {
  private Integer id;
  private LocalDateTime orderDate;
  private UserLimitedResponseDto user;
  private List<MealCompleteResponseDto> meals;
  private List<DrinkCompleteResponseDto> drinks;
  private Status status;
  private LocationCompleteResponseDto destination;
}
