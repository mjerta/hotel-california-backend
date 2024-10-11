package nl.mpdev.hotel_california_backend.services;

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

  public Ingredient addIngredients(Ingredient entity) {
    Ingredient.IngredientBuilder builder = Ingredient.builder();
    if(entity.getName() != null) {
      builder.name(entity.getName());
    }
    return ingredientRepository.save(builder.build());
  }
}
