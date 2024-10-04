package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.meals.MealCompleteMapper;
import nl.mpdev.hotel_california_backend.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/meals")
public class MealController {


  private final MealCompleteMapper mealCompleteMapper;
  private final MealService mealService;

  public MealController(MealCompleteMapper mealCompleteMapper, MealService mealService) {
    this.mealCompleteMapper = mealCompleteMapper;
    this.mealService = mealService;
  }

  // GET

  @GetMapping("")
  public ResponseEntity<List<MealCompleteResponseDto>> getMeals() {
    List<MealCompleteResponseDto> meals = mealService.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList());
    return ResponseEntity.ok().body(meals);

  }

  // POST


  // PUT


  // PATCH
}
