package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/drinks")
public class DrinkController {

  private final DrinkService drinkService;

  public DrinkController(DrinkService drinkService) {
    this.drinkService = drinkService;
  }
}
