package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.ImageMeal;
import nl.mpdev.hotel_california_backend.repositories.MealImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageMealService {

  private final MealImageRepository mealImageRepository;

  public ImageMealService(MealImageRepository mealImageRepository) {
    this.mealImageRepository = mealImageRepository;
  }

  public ImageMeal getImageById(Integer id) {
    return mealImageRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No meal found"));
  }
}
