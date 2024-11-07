package nl.mpdev.hotel_california_backend.mappers.drinks;

import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.response.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkIdRequestDto;
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
      .isAlcoholic(dto.getIsAlcoholic())
      .size(dto.getSize())
      .measurement(dto.getMeasurement())
      .build();
  }

  public Drink toEntity(DrinkIdRequestDto dto) {
    if(dto == null){
      return null;
    }
    return Drink.builder()
      .id(dto.getId())
      .build();
  }

  public DrinkCompleteResponseDto toDto(Drink entity) {
    if(entity == null) {
      return null;
    }
    return DrinkCompleteResponseDto.builder()
      .id(entity.getId())
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .isAlcoholic(entity.getIsAlcoholic())
      .size(entity.getSize())
      .measurement(entity.getMeasurement())
      .build();
  }
}
