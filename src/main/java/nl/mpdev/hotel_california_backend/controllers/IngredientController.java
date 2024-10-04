package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.services.IngredientService;
import org.springframework.stereotype.Controller;

@Controller
public class IngredientController {

  private final IngredientService ingredientService;

  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }
}
