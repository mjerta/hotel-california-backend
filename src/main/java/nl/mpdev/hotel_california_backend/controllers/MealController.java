package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  public MealController(MealCompleteMapper mealCompleteMapper, MealService mealService) {
    this.mealCompleteMapper = mealCompleteMapper;
    this.mealService = mealService;
  }

  // GET

  @Operation(summary = "public", description = "Send a get request with an id")
  @ApiResponse(responseCode = "200", description = "Returns a meal with a complete view of the entity")
  @GetMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> getMealById(@PathVariable Integer id) {
    MealCompleteResponseDto responseDto = mealCompleteMapper.toDto(mealService.getMealById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @Operation(summary = "public", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a list of meals with a complete view of the entity")
  @GetMapping("")
  public ResponseEntity<List<MealCompleteResponseDto>> getMeals() {
    List<MealCompleteResponseDto> meals = mealService.getMeals().stream().map(mealCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(meals);
  }

  // POST

  @Operation(summary = "ROLE_MANAGER" , description = "Send a post request with a json object with a limited view of a meal")
  @ApiResponse(responseCode = "201", description = "Returns a single object of the meal that's being added with a complete view")
  @PostMapping("")
  public ResponseEntity<MealCompleteResponseDto> addMeal(@Valid @RequestBody MealLimitedRequestDto requestDto) {
    Meal meal = mealService.addMeal(mealCompleteMapper.toEntity(requestDto));
    MealCompleteResponseDto responseDto = mealCompleteMapper.toDto(meal);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }
  // PUT

  @Operation(summary = "ROLE_MANAGER" , description = "Send a put request with an id and a json object with a update view of a meal, empty properties will be set null")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the meal that's has been updated with a complete view")
  @PutMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMeal(@PathVariable Integer id,
                                                            @Valid @RequestBody MealUpdateRequestDto requestDto) {
    Meal meal = mealService.updateMeal(id, requestDto);
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }

  // PATCH

  @Operation(summary = "ROLE_MANAGER" , description = "Send a patch request with and id and a json object with a update view of a drink, empty properties will beholds its original value")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the meal that's has been updated with a complete view")
  @PatchMapping("/{id}")
  public ResponseEntity<MealCompleteResponseDto> updateMealFields(@PathVariable Integer id,
                                                                  @Valid @RequestBody MealUpdateRequestDto requestDto) {

    Meal meal = mealService.updateMealFields(id, requestDto);
    return ResponseEntity.ok().body(mealCompleteMapper.toDto(meal));
  }

  // DELETE

  @Operation(summary = "ROLE_MANAGER" , description = "Send a delete request with an id")
  @ApiResponse(responseCode = "204", description = "Returns the value void")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMeal(@PathVariable Integer id) {
    mealService.deleteMeal(id);
  }
}
