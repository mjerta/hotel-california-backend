package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.request.MealUpdateRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.response.MealCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.meals.MealCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Meal;
import nl.mpdev.hotel_california_backend.repositories.MealRepository;
import nl.mpdev.hotel_california_backend.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
public class MealController {

  private final MealCompleteMapper mealCompleteMapper;
  private final MealService mealService;
  private final MealRepository mealRepository;

  public MealController(MealCompleteMapper mealCompleteMapper, MealService mealService, MealRepository mealRepository) {
    this.mealCompleteMapper = mealCompleteMapper;
    this.mealService = mealService;
    this.mealRepository = mealRepository;
  }

  // GET

  @GetMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> getMealById(@PathVariable Integer id) {
    MealCompleteResponseDto responseDto = mealCompleteMapper.toDto(mealService.getMealById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("")
  public ResponseEntity<List<MealCompleteResponseDto>> getMeals() {
    List<MealCompleteResponseDto> meals = mealService.getMeals().stream().map(mealCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(meals);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<MealCompleteResponseDto> addMeal(@Valid @RequestBody MealLimitedRequestDto requestDto) {
    Meal meal = mealService.addMeal(mealCompleteMapper.toEntity(requestDto));
    MealCompleteResponseDto responseDto = mealCompleteMapper.toDto(meal);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }
  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMeal(@PathVariable Integer id,
                                                            @Valid @RequestBody MealUpdateRequestDto requestDto) {
    Meal meal = mealService.updateMeal(id, requestDto);
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }

  // PATCH

  @PatchMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMealFields(@PathVariable Integer id,
                                                                  @Valid @RequestBody MealUpdateRequestDto requestDto) {

    Meal meal = mealService.updateMealFields(id, requestDto);
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }

  // DELETE

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMeal(@PathVariable Integer id) {
    mealService.deleteMeal(id);
  }
}
