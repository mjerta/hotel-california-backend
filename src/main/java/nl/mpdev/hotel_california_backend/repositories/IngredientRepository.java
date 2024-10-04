package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
