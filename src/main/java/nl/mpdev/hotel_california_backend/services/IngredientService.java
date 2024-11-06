package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public void deleteIngredient(Integer id) {
    ingredientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Ingredient not found"));
    ingredientRepository.deleteById(id);
  }

  public Ingredient addIngredient(Ingredient entity) {
    Ingredient.IngredientBuilder builder = Ingredient.builder();
    builder.name(entity.getName());
    return ingredientRepository.save(builder.build());
  }

  public Ingredient updateIngredient(Integer id, IngredientLimitedRequestDto requestDto) {
    Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Ingredient not found"));
    Ingredient.IngredientBuilder builder = ingredient.toBuilder();
    builder.name(requestDto.getName());
    return ingredientRepository.save(builder.build());
  }
}
