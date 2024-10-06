package nl.mpdev.hotel_california_backend.mappers.ingredients;

import nl.mpdev.hotel_california_backend.dtos.ingredients.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.IngredientCompleteResponseDto;
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
