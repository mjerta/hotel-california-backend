package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.*;
import nl.mpdev.hotel_california_backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final MealRepository mealRepository;
  private final DrinkRepository drinkRepository;
  private final LocationRepository locationRepository;
  private final UserRepository userRepository;

  public OrderService(OrderRepository orderRepository, MealRepository mealRepository, DrinkRepository drinkRepository,
                      LocationRepository locationRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.mealRepository = mealRepository;
    this.drinkRepository = drinkRepository;
    this.locationRepository = locationRepository;
    this.userRepository = userRepository;
  }

  public Order getOrderById(Integer id) {
    return orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

  public Order addOrder(Order entity) {

    List<Meal> existinMeals = entity.getMeals().stream()
      .map(meal -> mealRepository.findById(meal.getId())
        .orElseThrow(() -> new RecordNotFoundException()))
      .toList();

    List<Drink> existingDrinks = entity.getDrinks().stream()
      .map(drink -> drinkRepository.findById(drink.getId())
        .orElseThrow(() -> new RecordNotFoundException()))
      .toList();

    Location existingLocation = locationRepository.findById(entity.getDestination().getId()).orElseThrow(() -> new RecordNotFoundException());
    User existinUser = userRepository.findById(entity.getUser().getId()).orElseThrow(() -> new RecordNotFoundException());

    entity = entity.toBuilder()
      .user(existinUser)
      .orderDate(LocalDateTime.now())
      .meals(existinMeals)
      .drinks(existingDrinks)
      .destination(existingLocation)
      .build();

    return orderRepository.save(entity);
  }
}
