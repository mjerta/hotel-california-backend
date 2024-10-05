package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {

  private final DrinkRepository drinkRepository;

  public DrinkService(DrinkRepository drinkRepository) {
    this.drinkRepository = drinkRepository;
  }
}
