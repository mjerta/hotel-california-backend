package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.repositories.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

  private final MealRepository mealRepository;

  public MealService(MealRepository mealRepository) {
    this.mealRepository = mealRepository;
  }

  public List<Meal> getMeals() {
    return mealRepository.findAll();
  }

  public Meal getMealById(Integer id) {
    return mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found"));
  }
}
