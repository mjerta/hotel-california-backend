package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealUpdateRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.repositories.IngredientRepository;
import nl.mpdev.hotel_california_backend.repositories.MealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

  private final ServiceHelper serviceHelper;
  private final MealRepository mealRepository;
  private final IngredientRepository ingredientRepository;

  public MealService(ServiceHelper serviceHelper, MealRepository mealRepository, IngredientRepository ingredientRepository) {
    this.serviceHelper = serviceHelper;
    this.mealRepository = mealRepository;
    this.ingredientRepository = ingredientRepository;
  }


  public Meal getMealById(Integer id) {
    return mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public List<Meal> getMeals() {
    return mealRepository.findAll();
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

  public Meal updateMeal(Integer id, MealUpdateRequestDto requestDto) {
    Meal existingMeal = mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    List<Ingredient> existingIngredients = existingMeal.getIngredients().stream()
      .map(ingredient -> ingredientRepository.findById(ingredient.getId())
        .orElseThrow(() -> new RecordNotFoundException("Ingredient not found: " + ingredient.getId())))
      .toList();
    List<Ingredient> updatedIngredients = new ArrayList<>();
    for (Ingredient existingIngredient : existingIngredients) {
      for (IngredientCompleteRequestDto updtated : requestDto.getIngredients()) {
        if (updtated.getId().equals(existingIngredient.getId()))
          updatedIngredients.add(ingredientRepository.save(existingIngredient.toBuilder()
            .name(updtated.getName())
            .build()));
      }
    }
    existingMeal = existingMeal.toBuilder()
      .name(requestDto.getName())
      .description(requestDto.getDescription())
      .price(requestDto.getPrice())
      .image(requestDto.getImage())
      .ingredients(updatedIngredients)
      .build();
    return mealRepository.save(existingMeal);
  }

  public Meal updateMealFields(Integer id, MealUpdateRequestDto requestDto) {
    Meal existingMeal = mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    serviceHelper.setFieldsIfNotNUll(existingMeal, requestDto);
    updateIngredients(existingMeal, requestDto.getIngredients());
    return mealRepository.save(existingMeal);
  }

  public void deleteMeal(Integer id) {
    mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    mealRepository.deleteById(id);
  }

  private void updateIngredients(Meal existingMeal, List<IngredientCompleteRequestDto> incomingIngredients) {
    List<Ingredient> existingIngredients = existingMeal.getIngredients();
    for (IngredientCompleteRequestDto incoming : incomingIngredients) {
      Optional<Ingredient> existingIngredientOpt = existingIngredients.stream()
        .filter(existing -> existing.getId().equals(incoming.getId()))
        .findFirst();

      if (existingIngredientOpt.isPresent()) {
        // Update existing ingredient
        Ingredient existingIngredient = existingIngredientOpt.get();
        existingIngredient = existingIngredient.toBuilder()
          // Add other fields if needed
          .name(incoming.getName())
          .build();
        ingredientRepository.save(existingIngredient);
      }
    }
  }
}