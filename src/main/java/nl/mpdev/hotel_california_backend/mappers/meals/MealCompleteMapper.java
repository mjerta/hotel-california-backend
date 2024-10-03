package nl.mpdev.hotel_california_backend.mappers.meals;

import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Meal;
import org.springframework.stereotype.Component;

@Component
public class MealCompleteMapper {

  public Meal toEntity(MealCompleteRequestDto dto) {
    if (dto == null) {
      return null;
    }

    return Meal.builder()
      .price(dto.getPrice())
      .name(dto.getName())
      .description(dto.getDescription())
      .price(dto.getPrice())
      .image(dto.getImage())
      .build();
  }


  public MealCompleteResponseDto toDto(Meal entity) {
    return MealCompleteResponseDto.builder()
      .price(entity.getPrice())
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .image(entity.getImage())
      .build();
  }

}
