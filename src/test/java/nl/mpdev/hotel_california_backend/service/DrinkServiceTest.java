package nl.mpdev.hotel_california_backend.service;

import nl.mpdev.hotel_california_backend.dtos.drinks.request.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import nl.mpdev.hotel_california_backend.services.DrinkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

  @Mock
  DrinkRepository drinkRepository;

  @Spy
  private ServiceHelper serviceHelper;

  @InjectMocks
  DrinkService drinkService;

  @Test
  @DisplayName("getDrinkById - ")
  void getDrinkById() {
    // Arrange

    // Drink
    var name = "Fanta";
    var size = 50;
    var measurement = "cc";
    var price = 19.99;
    var description = "erg lekker";
    var isAlcoholic = false;
    // Order
    Drink drink = Drink.builder()
      .id(123)
      .name(name)
      .size(size)
      .measurement(measurement)
      .price(price)
      .description(description)
      .isAlcoholic(isAlcoholic)
      .build();

    List<Drink> drinks = List.of(drink);
    Order order = Order.builder()
      .id(300)
      .drinks(drinks)
      .build();

    List<Order> orders = List.of(order);
    drink = drink.toBuilder().orders(orders).build();
    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(drink));
    // Act
    Drink result = drinkService.getDrinkById(123);

    // Assert
    assertEquals(name, result.getName());
    assertEquals(size, result.getSize());
    assertEquals(measurement, result.getMeasurement());
    assertEquals(price, result.getPrice());
    assertEquals(description, result.getDescription());
    assertEquals(isAlcoholic, result.getIsAlcoholic());
    assertEquals(1, result.getOrders().size());
    assertEquals(order.getDrinks(), drinks);
  }

  @Test
  @DisplayName("getDrinkById - should throw RecordNotFoundException when drink is not found")
  // This test is enough to cover all other methods of the service, since they using the same exception
  void getDrinkById_DrinkNotFound_ThrowsException() {
    // Arrange
    Integer id = 999;
    when(drinkRepository.findById(id)).thenReturn(Optional.empty());
    // Act & Assert
    assertThrows(RecordNotFoundException.class, () -> drinkService.getDrinkById(id));
  }

  @Test
  @DisplayName("getAllDrinks - testing all fields and relations")
  void getDrinks() {
    // Arrange

    Drink drink1 = Drink.builder()
      .id(123)
      .name("Fanta")
      .size(50)
      .measurement("cc")
      .price(19.99)
      .description("erg lekker")
      .isAlcoholic(false)
      .build();

    Drink drink2 = Drink.builder()
      .id(124)
      .name("Coke")
      .size(33)
      .measurement("cl")
      .price(15.99)
      .description("refreshing")
      .isAlcoholic(false)
      .build();

    List<Drink> drinks = List.of(drink1, drink2);

    Order order = Order.builder()
      .id(300)
      .drinks(drinks)
      .build();

    // Adding the relation
    drink1 = drink1.toBuilder().orders(List.of(order)).build();
    drink2 = drink2.toBuilder().orders(List.of(order)).build();

    when(drinkRepository.findAll()).thenReturn(List.of(drink1, drink2));

    // Act
    List<Drink> result = drinkService.getDrinks();

    // Assert
    assertEquals(2, result.size());

    Drink drinkResult1 = result.get(0);
    assertEquals("Fanta", drinkResult1.getName());
    assertEquals(50, drinkResult1.getSize());
    assertEquals("cc", drinkResult1.getMeasurement());
    assertEquals(19.99, drinkResult1.getPrice());
    assertEquals("erg lekker", drinkResult1.getDescription());
    assertFalse(drinkResult1.getIsAlcoholic());
    assertEquals(1, drinkResult1.getOrders().size());
    assertEquals(300, drinkResult1.getOrders().get(0).getId());

    Drink drinkResult2 = result.get(1);
    assertEquals("Coke", drinkResult2.getName());
    assertEquals(33, drinkResult2.getSize());
    assertEquals("cl", drinkResult2.getMeasurement());
    assertEquals(15.99, drinkResult2.getPrice());
    assertEquals("refreshing", drinkResult2.getDescription());
    assertFalse(drinkResult2.getIsAlcoholic());
    assertEquals(1, drinkResult2.getOrders().size());
    // Testing the relation
    assertEquals(1, drinkResult2.getOrders().size());
    assertEquals(300, drinkResult2.getOrders().get(0).getId());

    // Testing the other side of the relation
    Order orderResult1 = drinkResult1.getOrders().get(0);
    assertEquals(2, orderResult1.getDrinks().size());
    assertEquals("Fanta", orderResult1.getDrinks().get(0).getName());

    Order orderResult2 = drinkResult2.getOrders().get(0);
    // OrderResult2 is the same order coming from a different drink.
    assertEquals(2, orderResult2.getDrinks().size());
    assertEquals("Coke", orderResult2.getDrinks().get(1).getName());
  }

  @Test
  @DisplayName("addDrink - should save and return new drink")
  void addDrink() {
    // Arrange
    Drink drink = Drink.builder()
      .id(124)
      .name("Coca Cola")
      .size(330)
      .measurement("ml")
      .price(2.5)
      .description("Classic taste")
      .isAlcoholic(false)
      .orders(null) //Assuming no orders is not attached to this drink - This would be set from its parent
      .build();

    when(drinkRepository.save(any(Drink.class))).thenReturn(drink);

    // Act
    Drink result = drinkService.addDrink(drink);

    // Assert
    assertEquals("Coca Cola", result.getName());

  }

  @Test
  @DisplayName("updateDrink - testing the Put update")
  void updateDrink() {
    // Arrange
    Integer id = 1;

    Drink existingDrink = Drink.builder()
      .id(id)
      .name("Sprite")
      .size(330)
      .measurement("ml")
      .price(2.0)
      .description("Lemon-lime soda")
      .isAlcoholic(false)
      .orders(null) //Assuming no orders is not attached to this drink - This would be set from its parent
      .build();

    DrinkCompleteRequestDto requestDto = DrinkCompleteRequestDto.builder()
      .name("Heineken")
      .size(33)
      .measurement("ml")
      .price(2.0)
      .description("Lekker biertje")
      .isAlcoholic(true)
      .build();

    Drink updatedDrink = Drink.builder()
      .id(id)
      .name(requestDto.getName())
      .size(requestDto.getSize())
      .measurement(requestDto.getMeasurement())
      .price(requestDto.getPrice())
      .description(requestDto.getDescription())
      .isAlcoholic(requestDto.getIsAlcoholic())
      .build();

    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(existingDrink));
    when(drinkRepository.save(any(Drink.class))).thenReturn(updatedDrink);

    // Act
    Drink result = drinkService.updateDrink(1, requestDto);

    // Assert
    assertEquals(id, result.getId());
    assertEquals("Heineken", result.getName());
    assertEquals(33, result.getSize());
    assertEquals("ml", result.getMeasurement());
    assertEquals(2.0, result.getPrice());
    assertEquals("Lekker biertje", result.getDescription());
    assertTrue(result.getIsAlcoholic());
  }

  @Test
  @DisplayName("updateDrink - testing the Put update")
  void updateDrinkWithNullValues() {
    // Arrange
    Integer id = 1;

    Drink existingDrink = Drink.builder()
      .id(id)
      .name("Sprite")
      .size(330)
      .measurement("ml")
      .price(2.0)
      .description("Lemon-lime soda")
      .isAlcoholic(false)
      .orders(null) //Assuming no orders is not attached to this drink - This would be set from its parent
      .build();

    DrinkCompleteRequestDto requestDto = DrinkCompleteRequestDto.builder()
      .name(null)
      .size(null)
      .measurement("ml")
      .price(2.0)
      .description("Lekker biertje")
      .isAlcoholic(true)
      .build();

    Drink updatedDrink = Drink.builder()
      .id(id)
      .name(requestDto.getName())
      .size(requestDto.getSize())
      .measurement(requestDto.getMeasurement())
      .price(requestDto.getPrice())
      .description(requestDto.getDescription())
      .isAlcoholic(requestDto.getIsAlcoholic())
      .build();

    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(existingDrink));
    when(drinkRepository.save(any(Drink.class))).thenReturn(updatedDrink);

    // Act
    Drink result = drinkService.updateDrink(1, requestDto);

    // Assert
    assertEquals(id, result.getId());
    assertNull(result.getName());
    assertNull(result.getSize());
    assertEquals("ml", result.getMeasurement());
    assertEquals(2.0, result.getPrice());
    assertEquals("Lekker biertje", result.getDescription());
    assertTrue(result.getIsAlcoholic());
  }

  @Test
  @DisplayName("updateDrinkFields  - testing the PATCH update")
  void updateDrinkFields() {
    // Arrange
    Integer id = 1;
    Drink existingDrink = Drink.builder()
      .id(id)
      .name("Sprite")
      .size(330)
      .measurement("ml")
      .price(2.0)
      .description("Lemon-lime soda")
      .isAlcoholic(false)
      .build();

    DrinkCompleteRequestDto requestDto = DrinkCompleteRequestDto.builder()
      .name("Heineken")
      .size(null)  // Nullable field to test `setFieldsIfNotNull` logic
      .measurement("ml")
      .price(2.5)
      .description("Lekker biertje")
      .isAlcoholic(true)
      .build();

    // Act
    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(existingDrink));
    when(drinkRepository.save(any(Drink.class))).thenReturn(existingDrink);

    // Act
    Drink updatedDrink = drinkService.updateDrinkFields(id, requestDto);

    // Assert
    assertEquals("Heineken", updatedDrink.getName());
    assertEquals(330, updatedDrink.getSize());       // size remains unchanged
    assertEquals("ml", updatedDrink.getMeasurement());
    assertEquals(2.5, updatedDrink.getPrice());       // new price should be set
    assertEquals("Lekker biertje", updatedDrink.getDescription());
    assertEquals(true, updatedDrink.getIsAlcoholic());

    verify(serviceHelper).setFieldsIfNotNUll(existingDrink, requestDto);
  }

  @Test
  @DisplayName("deleteDrinks - testing the DELETE endpoint")
  void deleteDrink() {
    // Arrange
    Integer id = 125;
    Drink drink = Drink.builder()
      .isAlcoholic(true)
      .orders(null)
      .description("Heerlijk biertje")
      .name("Corona")
      .price(3.99)
      .build();

    when(drinkRepository.findById(anyInt())).thenReturn(Optional.of(drink));
    doNothing().when(drinkRepository).deleteById(anyInt());

    // Act
    drinkService.deleteDrink(id);

    // Assert
    verify(drinkRepository, times(1)).deleteById(id);
  }
}
