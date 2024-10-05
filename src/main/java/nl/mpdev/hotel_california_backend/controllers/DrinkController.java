package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/drinks")
public class DrinkController {

  private final DrinkService drinkService;
  private final DrinkCompleteMapper drinkCompleteMapper;

  public DrinkController(DrinkService drinkService, DrinkCompleteMapper drinkCompleteMapper) {
    this.drinkService = drinkService;
    this.drinkCompleteMapper = drinkCompleteMapper;
  }

  // GET

  @GetMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> getDrinkById(@PathVariable Integer id) {
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drinkService.getDrinkById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("")
  public ResponseEntity<List<DrinkCompleteResponseDto>> getDrinks() {
    List<DrinkCompleteResponseDto> drinks = drinkService.getDrinks().stream().map(drinkCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(drinks);
  }
}
