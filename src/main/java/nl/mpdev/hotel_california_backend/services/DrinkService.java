package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {

  private final DrinkRepository drinkRepository;

  public DrinkService(DrinkRepository drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<Drink> getDrinks() {
    return drinkRepository.findAll();
  }

}
