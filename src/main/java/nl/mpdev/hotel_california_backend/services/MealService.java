package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.repositories.IngredientRepository;
import nl.mpdev.hotel_california_backend.repositories.MealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {

  private final MealRepository mealRepository;
  private final IngredientRepository ingredientRepository;

  public MealService(MealRepository mealRepository, IngredientRepository ingredientRepository) {
    this.mealRepository = mealRepository;
    this.ingredientRepository = ingredientRepository;
  }

  public List<Meal> getMeals() {
    return mealRepository.findAll();
  }

  public Meal getMealById(Integer id) {
    return mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public Meal addMeal(Meal entity) {
    Meal savedMeal = mealRepository.save(entity);

    List<Ingredient> savedIngredients = savedMeal.getIngredients().stream()
      .map(ingredient -> Ingredient.builder()
        .meal(savedMeal)
        .name(ingredient.getName())
        .build())
      .map(ingredientRepository::save)
      .toList();

    return savedMeal.toBuilder()
      .ingredients(savedIngredients)
      .build();
  }

  public Meal updateMeal(Integer id, Meal entity) {
    Meal existingMeal = mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    List<Ingredient> existingIngredients = existingMeal.getIngredients().stream()
      .map(ingredient -> ingredientRepository.findById(ingredient.getId())
        .orElseThrow(() -> new RecordNotFoundException("Ingredient not found: " + ingredient.getId())))
      .toList();
    List<Ingredient> updatedIngredients = new ArrayList<>();
    for (Ingredient existingIngredient : existingIngredients) {
      for (Ingredient updtated : entity.getIngredients()) {
        if(updtated.getId().equals(existingIngredient.getId()))
          updatedIngredients.add(ingredientRepository.save(existingIngredient.toBuilder()
            .name(updtated.getName())
            .build()));
      }
    }
    existingMeal = existingMeal.toBuilder()
      .name(entity.getName())
      .description(entity.getDescription())
      .price(entity.getPrice())
      .image(entity.getImage())
      .ingredients(updatedIngredients)
      .build();
    return mealRepository.save(existingMeal);
  }
}