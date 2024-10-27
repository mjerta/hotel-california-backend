package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealUpdateRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.DuplicateRecordFound;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.repositories.IngredientRepository;
import nl.mpdev.hotel_california_backend.repositories.MealRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    return mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No meal found"));
  }

  public List<Meal> getMeals() {
    return mealRepository.findAll();
  }

  public Meal addMeal(Meal entity) {
    if(mealRepository.existsMealByName(entity.getName())) {
      throw new DuplicateRecordFound("Name already exists");
    }
    Meal savedMeal = mealRepository.save(entity);
    List<Ingredient> savedIngredients = null;
    if (savedMeal.getIngredients() != null) {
      savedIngredients = savedMeal.getIngredients().stream()
        .filter(Objects::nonNull)
        .map(ingredient -> Ingredient.builder()
          .meal(savedMeal)
          .name(ingredient.getName())
          .build())
        .map(ingredientRepository::save)
        .toList();
    }
    return savedMeal.toBuilder()
      .ingredients(savedIngredients)
      .build();
  }

  public Meal updateMeal(Integer id, MealUpdateRequestDto requestDto) {
    Meal existingMeal = mealRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException("Meal not found"));
    updateIngredients(existingMeal, requestDto.getIngredients());
    existingMeal = existingMeal.toBuilder()
      .name(requestDto.getName())
      .description(requestDto.getDescription())
      .price(requestDto.getPrice())
      .build();
    return mealRepository.save(existingMeal);
  }

  public Meal updateMealFields(Integer id, MealUpdateRequestDto requestDto) {
    Meal existingMeal = mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No meal found"));
    serviceHelper.setFieldsIfNotNUll(existingMeal, requestDto);
    updateIngredients(existingMeal, requestDto.getIngredients());
    return mealRepository.save(existingMeal);
  }

  public void deleteMeal(Integer id) {
    mealRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No meal found"));
    mealRepository.deleteById(id);
  }

  private void updateIngredients(Meal existingMeal, List<IngredientCompleteRequestDto> incomingIngredients) {
    // Create a map of existing ingredients by ID for easy lookup
    Map<Integer, Ingredient> existingIngredientsMap = existingMeal.getIngredients().stream()
      .collect(Collectors.toMap(Ingredient::getId, ingredient -> ingredient));
    // List of updated ingredients from the request
    // Add or update ingredients from the request
    for (IngredientCompleteRequestDto updatedDto : incomingIngredients) {
      Ingredient existingIngredient = existingIngredientsMap.get(updatedDto.getId());
      if (existingIngredient != null) {
        // Update existing ingredient
        ingredientRepository.save(existingIngredient.toBuilder()
          .name(updatedDto.getName())
          .build());
      } else {
        // Handle case if ingredient does not exist in the meal
        throw new RecordNotFoundException("Ingredient not found: " + updatedDto.getId());
      }
    }
  }
}