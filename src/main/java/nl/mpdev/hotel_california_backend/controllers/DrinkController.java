package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.response.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drinks")
@Tag(name = "Drink endpoints")
public class DrinkController {

  private final DrinkService drinkService;
  private final DrinkCompleteMapper drinkCompleteMapper;

  public DrinkController(DrinkService drinkService, DrinkCompleteMapper drinkCompleteMapper) {
    this.drinkService = drinkService;
    this.drinkCompleteMapper = drinkCompleteMapper;
  }

  // GET

  @Operation(summary = "Get a drink by id", description = "Returns a complete view of the drink entity")
  @GetMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> getDrinkById(@PathVariable Integer id) {
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drinkService.getDrinkById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @Operation(summary = "First method", tags = {"Order 2"})
  @GetMapping("")
  public ResponseEntity<List<DrinkCompleteResponseDto>> getDrinks() {
    List<DrinkCompleteResponseDto> drinks = drinkService.getDrinks().stream().map(drinkCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(drinks);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<DrinkCompleteResponseDto> addDrink(@Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.addDrink(drinkCompleteMapper.toEntity(requestDto));
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drink);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> updateDrink(@PathVariable Integer id, @Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.updateDrink(id, requestDto);
    return ResponseEntity.ok().body(drinkCompleteMapper.toDto(drink));
  }

  // Patch

  @PatchMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> updateDrinkFields(@PathVariable Integer id, @Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.updateDrinkFields(id, requestDto);
    return ResponseEntity.ok().body(drinkCompleteMapper.toDto(drink));
  }

  // DELETE

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDrink(@PathVariable Integer id) {
    drinkService.deleteDrink(id);
  }
}
