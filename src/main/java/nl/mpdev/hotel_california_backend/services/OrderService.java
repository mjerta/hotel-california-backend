package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.*;
import nl.mpdev.hotel_california_backend.models.enums.Status;
import nl.mpdev.hotel_california_backend.repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final MealRepository mealRepository;
  private final DrinkRepository drinkRepository;
  private final LocationRepository locationRepository;
  private final UserRepository userRepository;
  private final ServiceHelper serviceHelper;

  public OrderService(OrderRepository orderRepository, MealRepository mealRepository, DrinkRepository drinkRepository,
                      LocationRepository locationRepository, UserRepository userRepository, ServiceHelper serviceHelper) {
    this.orderRepository = orderRepository;
    this.mealRepository = mealRepository;
    this.drinkRepository = drinkRepository;
    this.locationRepository = locationRepository;
    this.userRepository = userRepository;
    this.serviceHelper = serviceHelper;
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

    Location existingLocation = locationRepository.findById(entity.getDestination().getId())
      .orElseThrow(() -> new RecordNotFoundException());
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

  public Order updateOrder(Integer id, OrderCompleteRequestDto requestDto) {
    Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());

    List<Meal> existinMeals = null;
    if (requestDto.getMeals() != null) {
      existinMeals = requestDto.getMeals().stream()
        .map(meal -> mealRepository.findById(meal.getId())
          .orElseThrow(() -> new RecordNotFoundException()))
        .toList();
    }

    List<Drink> existingDrinks = null;
    if (requestDto.getDrinks() != null) {
      existingDrinks = requestDto.getDrinks().stream()
        .map(drink -> drinkRepository.findById(drink.getId())
          .orElseThrow(() -> new RecordNotFoundException()))
        .toList();
    }
    Location existingLocation = null;
    if (requestDto.getDestination() != null) {
      existingLocation = locationRepository.findById(requestDto.getDestination().getId())
        .orElseThrow(() -> new RecordNotFoundException());
    }
    User existinUser = null;
    if (requestDto.getUser() != null) {
      existinUser = userRepository.findById(requestDto.getUser().getId()).orElseThrow(() -> new RecordNotFoundException());
    }

    existingOrder = existingOrder.toBuilder()
      .user(existinUser)
      .orderDate(LocalDateTime.now())
      .meals(existinMeals)
      .drinks(existingDrinks)
      .destination(existingLocation)
      .build();

    return orderRepository.save(existingOrder);
  }

  public Order updateOrderFields(Integer id, OrderCompleteRequestDto requestDto) {
    Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    // Handling meals
    List<Meal> newOrExistingMeal;
    if (requestDto.getMeals() != null) {
      newOrExistingMeal = requestDto.getMeals().stream()
        .map(meal -> mealRepository.findById(meal.getId()).orElseThrow(RecordNotFoundException::new))
        .toList();
    } else {
      newOrExistingMeal = existingOrder.getMeals();
    }
    // Handling drinks
    List<Drink> newOrExistingDrink;
    if (requestDto.getDrinks() != null) {
      newOrExistingDrink = requestDto.getDrinks().stream()
        .map(drink -> drinkRepository.findById(drink.getId())
          .orElseThrow(RecordNotFoundException::new))
        .toList();
    } else {
      newOrExistingDrink = existingOrder.getDrinks();
    }
    // Handling status
    Status newOrExistingStatus;
    if(requestDto.getStatus() != null) {
      newOrExistingStatus = requestDto.getStatus();
    } else {
      newOrExistingStatus = existingOrder.getStatus();
    }
    // Handling destination
    Location newOrExistingLocation;
    if (requestDto.getDestination() != null) {
      newOrExistingLocation = locationRepository.findById(requestDto.getDestination().getId())
        .orElseThrow(RecordNotFoundException::new);
    } else {
      newOrExistingLocation = existingOrder.getDestination();
    }
    // Handling user
    User newOrExistingUser;
    if (requestDto.getUser() != null) {
      newOrExistingUser = userRepository.findById(requestDto.getUser().getId())
        .orElseThrow(RecordNotFoundException::new);
    } else {
      newOrExistingUser = existingOrder.getUser();
    }

    // Updating the existing order with the new fields
    existingOrder = existingOrder.toBuilder()
      .user(newOrExistingUser)
      .meals(newOrExistingMeal)
      .drinks(newOrExistingDrink)
      .status(newOrExistingStatus)
      .destination(newOrExistingLocation)
      .build();

    return orderRepository.save(existingOrder);
  }

  // Perhaps I could be using something like this later as a helper method
  private <T, R> List<R> updateListOrKeepExisting(List<T> dtoList, List<R> existingList, Integer findId, JpaRepository<R, Integer> repository) {
    if (dtoList != null) {
      return dtoList.stream()
        .map(dto -> repository.findById(findId).orElseThrow(RecordNotFoundException::new))
        .collect(Collectors.toList());
    } else {
      return existingList;
    }
  }

  public void deleteOrder(Integer id) {
    orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    orderRepository.deleteById(id);
  }
}

