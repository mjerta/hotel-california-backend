package nl.mpdev.hotel_california_backend.mappers.ingredients;

import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.response.IngredientCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientCompleteMapper {
  public Ingredient toEntity(IngredientCompleteRequestDto dto) {
    if(dto == null) {
      return null;
    }
    return Ingredient.builder()
      .id(dto.getId())
      .name(dto.getName())
      .build();
  }

  public Ingredient toEntity(IngredientLimitedRequestDto dto) {
    if(dto == null) {
      return null;
    }
    return Ingredient.builder()
      .name(dto.getName())
      .build();
  }

  public IngredientCompleteResponseDto toDto(Ingredient entity) {
    if(entity == null) {
      return null;
    }
    return IngredientCompleteResponseDto.builder()
      .id(entity.getId())
      .name(entity.getName())
      .build();
  }
}
