package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.response.IngredientCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.ingredients.IngredientCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

  private final IngredientService ingredientService;
  private final IngredientCompleteMapper ingredientCompleteMapper;

  public IngredientController(IngredientService ingredientService, IngredientCompleteMapper ingredientCompleteMapper) {
    this.ingredientService = ingredientService;
    this.ingredientCompleteMapper = ingredientCompleteMapper;
  }

  // POST

  @Operation(summary = "ROLE_MANAGER", description = "Send a post request with an id an a json object with a limited view of a ingredient")
  @ApiResponse(responseCode = "201", description = "Returns a single object the ingredient that's being added with a complete view")
  @PostMapping("")
  public ResponseEntity<IngredientCompleteResponseDto> addIngredient(@RequestBody IngredientLimitedRequestDto requestDto) {
    Ingredient ingredient = ingredientService.addIngredient(ingredientCompleteMapper.toEntity(requestDto));
    IngredientCompleteResponseDto responseDto = ingredientCompleteMapper.toDto(ingredient);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT

  @Operation(summary = "ROLE_MANAGER", description = "Send a put request with an id and a json object with a limited view of a " +
    "ingredient, empty properties will be set null")
  @ApiResponse(responseCode = "200", description = "Returns a single object the ingredient that's being updated with a complete view")
  @PutMapping("/{id}")
  public ResponseEntity<IngredientCompleteResponseDto> updateIngredient(@PathVariable Integer id,
                                                                        @Valid @RequestBody IngredientLimitedRequestDto requestDto) {
    Ingredient ingredient = ingredientService.updateIngredient(id, requestDto);
    return ResponseEntity.ok(ingredientCompleteMapper.toDto(ingredient));
  }

  // DELETE

  @Operation(summary = "ROLE_MANAGER", description = "Send a delete request with an id")
  @ApiResponse(responseCode = "204", description = "Returns the value void")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteIngredient(@PathVariable Integer id) {
    ingredientService.deleteIngredient(id);
  }
}
