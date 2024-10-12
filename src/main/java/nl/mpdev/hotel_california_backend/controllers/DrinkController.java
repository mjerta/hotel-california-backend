package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

  @Operation(summary = "public", description = "Send a get request with an id")
  @ApiResponse(responseCode = "200", description = "Returns a drink with a complete view of the entity")
  @GetMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> getDrinkById(@PathVariable Integer id) {
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drinkService.getDrinkById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @Operation(summary = "public", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a list of drinks with a complete view of the entity")
  @GetMapping("")
  public ResponseEntity<List<DrinkCompleteResponseDto>> getDrinks() {
    List<DrinkCompleteResponseDto> drinks = drinkService.getDrinks().stream().map(drinkCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(drinks);
  }

  // POST

  @Operation(summary = "ROLE_MANAGER" , description = "Send a post request with json object with a complete view of a drink")
  @ApiResponse(responseCode = "201", description = "Returns a single object the drink that's being added")
  @PostMapping("")
  public ResponseEntity<DrinkCompleteResponseDto> addDrink(@Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.addDrink(drinkCompleteMapper.toEntity(requestDto));
    DrinkCompleteResponseDto responseDto = drinkCompleteMapper.toDto(drink);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT
  @Operation(summary = "ROLE_MANAGER" , description = "Send a put request with an id and a json object with a complete view of a drink, empty properties will be set null")
  @ApiResponse(responseCode = "200", description = "Returns a single object the drink that's has been updated")
  @PutMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> updateDrink(@PathVariable Integer id, @Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.updateDrink(id, requestDto);
    return ResponseEntity.ok().body(drinkCompleteMapper.toDto(drink));
  }

  // Patch

  @Operation(summary = "ROLE_MANAGER" , description = "Send a patch request with and id and a json object with a complete view of a drink, empty properties will beholds its original value")
  @ApiResponse(responseCode = "200", description = "Returns a single object the drink that's has been updated")
  @PatchMapping("/{id}")
  public ResponseEntity<DrinkCompleteResponseDto> updateDrinkFields(@PathVariable Integer id, @Valid @RequestBody DrinkCompleteRequestDto requestDto) {
    Drink drink = drinkService.updateDrinkFields(id, requestDto);
    return ResponseEntity.ok().body(drinkCompleteMapper.toDto(drink));
  }

  // DELETE

  @Operation(summary = "ROLE_MANAGER" , description = "Send a delete request with an id")
  @ApiResponse(responseCode = "204", description = "Returns value is void")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDrink(@PathVariable Integer id) {
    drinkService.deleteDrink(id);
  }
}
