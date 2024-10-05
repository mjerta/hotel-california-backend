package nl.mpdev.hotel_california_backend.mappers.orders;

import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.locations.LocationCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.meals.MealCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.user.UserLimitedMapper;
import nl.mpdev.hotel_california_backend.models.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderCompleteMapper {

  private final UserLimitedMapper userLimitedMapper;
  private final MealCompleteMapper mealCompleteMapper;
  private final DrinkCompleteMapper drinkCompleteMapper;
  private final LocationCompleteMapper locationCompleteMapper;

  public OrderCompleteMapper(UserLimitedMapper userLimitedMapper, MealCompleteMapper mealCompleteMapper,
                             DrinkCompleteMapper drinkCompleteMapper, LocationCompleteMapper locationCompleteMapper) {
    this.userLimitedMapper = userLimitedMapper;
    this.mealCompleteMapper = mealCompleteMapper;
    this.drinkCompleteMapper = drinkCompleteMapper;
    this.locationCompleteMapper = locationCompleteMapper;
  }

  public Order toEntity(OrderCompleteRequestDto dto) {
    if(dto == null) {
      return null;
    }
    return Order.builder()
      .user(userLimitedMapper.toEntity(dto.getUser()))
      .meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()))
      .drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()))
      .status(dto.getStatus())
      .destination(locationCompleteMapper.toEntity(dto.getDestination()))
      .build();
  }

  public OrderCompleteResponseDto toDto(Order entity) {
    if(entity == null) {
      return null;
    }
    return OrderCompleteResponseDto.builder()
      .id(entity.getId())
      .orderDate(entity.getOrderDate())
      .user(userLimitedMapper.toDto(entity.getUser()))
      .meals(entity.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList()))
      .drinks(entity.getDrinks().stream().map(drinkCompleteMapper::toDto).collect(Collectors.toList()))
      .status(entity.getStatus())
      .destination(locationCompleteMapper.toDto(entity.getDestination()))
      .build();
  }
}
