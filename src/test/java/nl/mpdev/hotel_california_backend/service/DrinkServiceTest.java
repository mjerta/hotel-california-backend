package nl.mpdev.hotel_california_backend.service;

import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

  @Mock
  DrinkRepository drinkRepository;

  @InjectMocks
  DrinkService drinkService;

  @Test
  @DisplayName("getDrinkById")
  void getDrinkById() {
    // Arrange

    var name = "Fanta";
    var size = 50;
    var measurement = "cc";
    var price = 19.99;
    var description = "erg lekker";
    var isAlchoholic = false;
    Drink drink = Drink.builder()
      .id(123)
      .name(name)
      .size(size)
      .measurement(measurement)
      .price(price)
      .description(description)
      .isAlcoholic(isAlchoholic)
      .build();


    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(drink));
    // Act
    Drink result = drinkService.getDrinkById(123);

    // Assert
    assertEquals(name, result.getName());
    assertEquals(size, result.getSize());
    assertEquals(measurement, result.getMeasurement());
    assertEquals(price, result.getPrice());
    assertEquals(description, result.getDescription());
    assertEquals(isAlchoholic, result.getIsAlcoholic());

  }

  @Test
  @DisplayName("getAllDrinks - should return a list of drinks")
  void getAllDrinks() {
    // Arrange
    Drink drink1 = Drink.builder().id(1).name("Pepsi").build();
    Drink drink2 = Drink.builder().id(2).name("Water").build();

    when(drinkRepository.findAll()).thenReturn(List.of(drink1, drink2));

    // Act
    List<Drink> result = drinkService.getDrinks();

    // Assert
    assertEquals(2, result.size());
    assertEquals("Pepsi", result.get(0).getName());
    assertEquals("Water", result.get(1).getName());
  }
}
