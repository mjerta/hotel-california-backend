package nl.mpdev.hotel_california_backend.mappers.orders;

import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.locations.LocationCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.meals.MealCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.user.UserLimitedMapper;
import nl.mpdev.hotel_california_backend.models.Location;
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
    if (dto == null) {
      return null;
    }
    Order.OrderBuilder orderBuilder = Order.builder();
    if (dto.getUser() != null) {
      orderBuilder.user(userLimitedMapper.toEntity(dto.getUser()));
    }
    if (dto.getMeals() != null) {
      orderBuilder.meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDrinks() != null) {
      orderBuilder.drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDestination() != null) {
      orderBuilder.destination(locationCompleteMapper.toEntity(dto.getDestination()));
    }
    orderBuilder.status(dto.getStatus());

    return orderBuilder.build();

  }

  public OrderCompleteResponseDto toDto(Order entity) {
    if (entity == null) {
      return null;
    }

    OrderCompleteResponseDto.OrderCompleteResponseDtoBuilder builder = OrderCompleteResponseDto.builder();

    builder.id(entity.getId());
    builder.orderDate(entity.getOrderDate());
    if (entity.getUser() != null) {
      builder.user(userLimitedMapper.toDto(entity.getUser()));
    }
    if (entity.getMeals() != null) {
      builder.meals(entity.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList()));
    }
    if (entity.getDrinks() != null) {
      builder.drinks(entity.getDrinks().stream().map(drinkCompleteMapper::toDto).collect(Collectors.toList()));
    }
    builder.status(entity.getStatus());
    if (entity.getDestination() != null) {
      Location location = entity.getDestination().toBuilder().build();
      builder.destination(locationCompleteMapper.toDto(entity.getDestination()));
    }

    return builder.build();

//    return OrderCompleteResponseDto.builder()
//      .id(entity.getId())
//      .orderDate(entity.getOrderDate())
//      .user(userLimitedMapper.toDto(entity.getUser()))
//      .meals(entity.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList()))
//      .drinks(entity.getDrinks().stream().map(drinkCompleteMapper::toDto).collect(Collectors.toList()))
//      .status(entity.getStatus())
//      .destination(locationCompleteMapper.toDto(entity.getDestination()))
//      .build();
  }
}
