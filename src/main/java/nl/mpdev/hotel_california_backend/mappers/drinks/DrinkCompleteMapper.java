package nl.mpdev.hotel_california_backend.mappers.drinks;

import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Drink;
import org.springframework.stereotype.Component;

@Component
public class DrinkCompleteMapper {
  public Drink toEntity(DrinkCompleteRequestDto dto) {
    if (dto == null) {
      return null;
    }
    return Drink.builder()
      .name(dto.getName())
      .description(dto.getDescription())
      .price(dto.getPrice())
      .image(dto.getImage())
      .isAlcoholic(dto.getIsAlcoholic())
      .size(dto.getSize())
      .measurement(dto.getMeasurement())
      .build();
  }

  public DrinkCompleteResponseDto toDto(Drink drink) {
    return DrinkCompleteResponseDto.builder()
      .id(drink.getId())
      .name(drink.getName())
      .description(drink.getDescription())
      .price(drink.getPrice())
      .image(drink.getImage())
      .isAlcoholic(drink.getIsAlcoholic())
      .size(drink.getSize())
      .measurement(drink.getMeasurement())
      .build();
  }
}
