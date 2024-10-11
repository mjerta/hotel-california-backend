package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.ingredients.request.IngredientLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.ingredients.response.IngredientCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.ingredients.IngredientCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.orders.OrderCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Ingredient;
import nl.mpdev.hotel_california_backend.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

  private final IngredientService ingredientService;
  private final IngredientCompleteMapper ingredientCompleteMapper;
  private final OrderCompleteMapper orderCompleteMapper;

  public IngredientController(IngredientService ingredientService, IngredientCompleteMapper ingredientCompleteMapper,
                              OrderCompleteMapper orderCompleteMapper) {
    this.ingredientService = ingredientService;
    this.ingredientCompleteMapper = ingredientCompleteMapper;
    this.orderCompleteMapper = orderCompleteMapper;
  }

  // POST
  @PostMapping("")
  public ResponseEntity<IngredientCompleteResponseDto> addIngredient(@RequestBody IngredientLimitedRequestDto requestDto) {
    Ingredient ingredient = ingredientService.addIngredient(ingredientCompleteMapper.toEntity(requestDto));
    IngredientCompleteResponseDto responseDto = ingredientCompleteMapper.toDto(ingredient);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT
  @PutMapping("/{id}")
  public ResponseEntity<IngredientCompleteResponseDto> updateIngredient(@PathVariable Integer id,
                                                                        @Valid @RequestBody IngredientLimitedRequestDto requestDto) {
    Ingredient ingredient = ingredientService.updateIngredient(id, requestDto);
    return ResponseEntity.ok(ingredientCompleteMapper.toDto(ingredient));
  }

  // DELETE
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteIngredient(@PathVariable Integer id) {
    ingredientService.deleteIngredient(id);
  }
}
