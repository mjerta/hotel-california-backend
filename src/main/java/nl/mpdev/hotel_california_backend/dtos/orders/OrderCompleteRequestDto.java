package nl.mpdev.hotel_california_backend.dtos.orders;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteRequestDto;
import nl.mpdev.hotel_california_backend.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderCompleteRequestDto {
  private LocalDateTime orderDate;
  private User user;
  private List<MealCompleteRequestDto> meals;
  private List<DrinkCompleteRequestDto> drinks;



}
