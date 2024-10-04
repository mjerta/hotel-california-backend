package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public void deleteIngredient(Integer id) {
    ingredientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    ingredientRepository.deleteById(id);
  }
}
