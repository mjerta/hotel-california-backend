package nl.mpdev.hotel_california_backend.mappers.meals;

import nl.mpdev.hotel_california_backend.dtos.meals.request.MealLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealTestRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.response.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealIdRequestDto;
import nl.mpdev.hotel_california_backend.mappers.ingredients.IngredientCompleteMapper;
import nl.mpdev.hotel_california_backend.models.ImageMeal;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.models.MealTest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class MealCompleteMapper {

  private final IngredientCompleteMapper ingredientCompleteMapper;

  public MealCompleteMapper(IngredientCompleteMapper ingredientCompleteMapper) {
    this.ingredientCompleteMapper = ingredientCompleteMapper;
  }

  public Meal toEntity(MealLimitedRequestDto dto) {
    if (dto == null) {
      return null;
    }
    Meal.MealBuilder mealBuilder = Meal.builder();
    mealBuilder.name(dto.getName());
    mealBuilder.description(dto.getDescription());
    mealBuilder.price(dto.getPrice());
    mealBuilder.image(dto.getImage());
    if (dto.getIngredients() != null) {
      mealBuilder.ingredients(dto.getIngredients().stream().map(ingredientCompleteMapper::toEntity).collect(Collectors.toList()));
    }
    return mealBuilder.build();
  }

  public Meal toEntity(MealIdRequestDto dto) {
    if (dto == null) {
      return null;
    }
    return Meal.builder()
      .id(dto.getId())
      .build();
  }


  public MealTest toEntity(MealTestRequestDto dto) throws IOException {
    if (dto == null) {
      return null;
    }
    MealTest.MealTestBuilder mealTestBuilder = MealTest.builder();
    mealTestBuilder.name(dto.getName());
    mealTestBuilder.description(dto.getDescription());
    mealTestBuilder.price(dto.getPrice());
    ImageMeal test = ImageMeal.builder()
      .data(dto.getImage().getBytes())
      .size(dto.getImage().getSize())
      .contentType(dto.getImage().getContentType())
      .name(dto.getImage().getOriginalFilename())
      .build();

    mealTestBuilder.image(test);
    return mealTestBuilder.build();

  }

  public MealCompleteResponseDto toDto(Meal entity) {

    MealCompleteResponseDto.MealCompleteResponseDtoBuilder builder = MealCompleteResponseDto.builder();
    builder.id(entity.getId());
    builder.price(entity.getPrice());
    builder.name(entity.getName());
    builder.description(entity.getDescription());
    builder.price(entity.getPrice());
    builder.image(entity.getImage());
    if (entity.getIngredients() != null) {
      builder.ingredients(entity.getIngredients().stream().map(ingredientCompleteMapper::toDto).collect(Collectors.toList()));
    }
    return builder.build();
  }
}
