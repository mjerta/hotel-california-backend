package nl.mpdev.hotel_california_backend.mappers.meals;

import nl.mpdev.hotel_california_backend.dtos.meals.request.MealLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.response.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealIdRequestDto;
import nl.mpdev.hotel_california_backend.mappers.ingredients.IngredientCompleteMapper;
import nl.mpdev.hotel_california_backend.models.ImageMeal;
import nl.mpdev.hotel_california_backend.models.Meal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class MealCompleteMapper {

  private final IngredientCompleteMapper ingredientCompleteMapper;

  public MealCompleteMapper(IngredientCompleteMapper ingredientCompleteMapper) {
    this.ingredientCompleteMapper = ingredientCompleteMapper;
  }

  public Meal toEntity(MealLimitedRequestDto dto) throws IOException {
    if (dto == null) {
      return null;
    }
    Meal.MealBuilder mealBuilder = Meal.builder();
    mealBuilder.name(dto.getName());
    mealBuilder.description(dto.getDescription());
    mealBuilder.price(dto.getPrice());

    ImageMeal.ImageMealBuilder imageMealBuilder = ImageMeal.builder();
    if (dto.getImage() != null) {
      imageMealBuilder.data(dto.getImage().getBytes());
      imageMealBuilder.size(dto.getImage().getSize());
      imageMealBuilder.contentType(dto.getImage().getContentType());
      imageMealBuilder.name(dto.getImage().getOriginalFilename());
      mealBuilder.image(imageMealBuilder.build());
    }

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

  public MealCompleteResponseDto toDto(Meal entity) {

    MealCompleteResponseDto.MealCompleteResponseDtoBuilder builder = MealCompleteResponseDto.builder();
    builder.id(entity.getId());
    builder.price(entity.getPrice());
    builder.name(entity.getName());
    builder.description(entity.getDescription());
    builder.price(entity.getPrice());
    if (entity.getImage() != null) {
      String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/v1/image-meals/")
        .path(String.valueOf(entity.getImage().getId()))
        .toUriString();
      builder.image(imageUrl);
    }
    if (entity.getIngredients() != null) {
      builder.ingredients(entity.getIngredients().stream().map(ingredientCompleteMapper::toDto).collect(Collectors.toList()));
    }
    return builder.build();
  }
}
