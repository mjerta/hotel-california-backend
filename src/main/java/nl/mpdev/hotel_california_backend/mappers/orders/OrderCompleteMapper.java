package nl.mpdev.hotel_california_backend.mappers.orders;

import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderCompleteStaffRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderUpdateRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderNewRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.response.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.locations.LocationCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.meals.MealCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.users.UserCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.models.enums.Status;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderCompleteMapper {

  private final UserCompleteMapper userCompleteMapper;
  private final MealCompleteMapper mealCompleteMapper;
  private final DrinkCompleteMapper drinkCompleteMapper;
  private final LocationCompleteMapper locationCompleteMapper;

  public OrderCompleteMapper(UserCompleteMapper userCompleteMapper, MealCompleteMapper mealCompleteMapper,
                             DrinkCompleteMapper drinkCompleteMapper, LocationCompleteMapper locationCompleteMapper) {
    this.userCompleteMapper = userCompleteMapper;
    this.mealCompleteMapper = mealCompleteMapper;
    this.drinkCompleteMapper = drinkCompleteMapper;
    this.locationCompleteMapper = locationCompleteMapper;
  }

  public Order toEntity(OrderCompleteRequestDto dto) {
    if (dto == null) {
      return null;
    }
    Order.OrderBuilder orderBuilder = Order.builder();
    if (dto.getMeals() != null) {
      orderBuilder.meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDrinks() != null) {
      orderBuilder.drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDestination() != null) {
      orderBuilder.destination(locationCompleteMapper.toEntity(dto.getDestination()));
    }
    if (dto.getStatus() != null) {
      orderBuilder.status(dto.getStatus());
    }

    return orderBuilder.build();
  }

  public Order toEntity(OrderCompleteStaffRequestDto dto) {
    if (dto == null) {
      return null;
    }
    Order.OrderBuilder orderBuilder = Order.builder();
    if (dto.getMeals() != null) {
      orderBuilder.meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDrinks() != null) {
      orderBuilder.drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDestination() != null) {
      orderBuilder.destination(locationCompleteMapper.toEntity(dto.getDestination()));
    }
    if (dto.getStatus() != null) {
      orderBuilder.status(dto.getStatus());
    }

    return orderBuilder.build();
  }

  public Order toEntity(OrderUpdateRequestDto dto) {
    if (dto == null) {
      return null;
    }
    Order.OrderBuilder orderBuilder = Order.builder();
    if (dto.getMeals() != null) {
      orderBuilder.meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDrinks() != null) {
      orderBuilder.drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getStatus() != null) {
      orderBuilder.status(dto.getStatus());
    }
    return orderBuilder.build();
  }

  public Order toEntity(OrderNewRequestDto dto) {
    if (dto == null) {
      return null;
    }
    Order.OrderBuilder orderBuilder = Order.builder();
    if (dto.getMeals() != null) {
      orderBuilder.meals(dto.getMeals().stream().map(mealCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if (dto.getDrinks() != null) {
      orderBuilder.drinks(dto.getDrinks().stream().map(drinkCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    if(dto.getDestination() != null) {
      orderBuilder.destination(locationCompleteMapper.toEntity(dto.getDestination()));
    }
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
      builder.user(userCompleteMapper.toUserLimitedResponse(entity.getUser()));
    }
    if (entity.getMeals() != null) {
      builder.meals(entity.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList()));
    }
    if (entity.getDrinks() != null) {
      builder.drinks(entity.getDrinks().stream().map(drinkCompleteMapper::toDto).collect(Collectors.toList()));
    }
    if (entity.getStatus() != null) {
      builder.status(entity.getStatus());
    }
    if (entity.getDestination() != null) {
      builder.destination(locationCompleteMapper.toDto(entity.getDestination()));
    }
    if (entity.getOrderReference() != null) {
      builder.orderReference(entity.getOrderReference());
    }
    return builder.build();
  }
}
