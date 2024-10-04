package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.meals.MealCompleteResponseDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
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
    List<MealCompleteResponseDto> meals = mealService.getMeals().stream().map(mealCompleteMapper::toDto).collect(Collectors.toList());
    return ResponseEntity.ok().body(meals);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<MealCompleteResponseDto> addMeal(@Valid @RequestBody MealCompleteRequestDto requestDto) {
    Meal meal = mealService.addMeal(mealCompleteMapper.toEntity(requestDto));
    MealCompleteResponseDto responseDto = mealCompleteMapper.toDto(meal);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }
  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMeal(@PathVariable Integer id, @Valid @RequestBody MealCompleteRequestDto requestDto) {
    Meal meal = mealService.updateMeal(id, mealCompleteMapper.toEntity(requestDto));
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }

  // PATCH

  // PATCH
  @PatchMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMealFields(@PathVariable Integer id, @Valid @RequestBody MealCompleteRequestDto requestDto) {

    Meal meal = mealService.updateMealFields(id, mealCompleteMapper.toEntity(requestDto));
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }
}