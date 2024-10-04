package nl.mpdev.hotel_california_backend.mappers.meals;

import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.ingredients.IngredientCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Meal;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MealCompleteMapper {

  private final IngredientCompleteMapper ingredientCompleteMapper;

  public MealCompleteMapper(IngredientCompleteMapper ingredientCompleteMapper) {
    this.ingredientCompleteMapper = ingredientCompleteMapper;
  }

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
      .ingredients(dto.getIngredients().stream().map(ingredientCompleteMapper::toEntity).collect(Collectors.toList()))
      .build();
  }

  public MealCompleteResponseDto toDto(Meal entity) {
    return MealCompleteResponseDto.builder()
      .id(entity.getId())
      .price(entity.getPrice())
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .image(entity.getImage())
      .ingredients(entity.getIngredients().stream().map(ingredientCompleteMapper::toDto).collect(Collectors.toList()))
      .build();
  }
}
