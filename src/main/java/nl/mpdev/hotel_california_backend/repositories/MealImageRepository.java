package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.ImageMeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealImageRepository extends JpaRepository<ImageMeal, Integer> {
}
