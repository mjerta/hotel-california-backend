package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.drinks.DrinkCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

  // POST

  @PostMapping("")
  public ResponseEntity<DrinkCompleteResponseDto> addDrink(@Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.addDrink(drinkCompleteMapper.toEntity(requestDto));
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drink);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }


}