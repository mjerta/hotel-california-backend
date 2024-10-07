package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.*;
import nl.mpdev.hotel_california_backend.models.enums.Status;
import nl.mpdev.hotel_california_backend.repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    if (entity.getMeals() == null && entity.getDrinks() == null) {
      throw new GeneralException("At least a drink or meal needs to be filled");
    }
    List<Meal> existinMeals = null;
    if (entity.getMeals() != null) {
      existinMeals = entity.getMeals().stream()
        .map(meal -> mealRepository.findById(meal.getId())
          .orElseThrow(() -> new RecordNotFoundException()))
        .toList();
    }
    List<Drink> existingDrinks = null;
    if (entity.getDrinks() != null) {
      existingDrinks = entity.getDrinks().stream()
        .map(drink -> drinkRepository.findById(drink.getId())
          .orElseThrow(() -> new RecordNotFoundException()))
        .toList();
    }
    Location existingLocation = locationRepository.findById(entity.getDestination().getId())
      .orElseThrow(() -> new RecordNotFoundException("Destination not found"));
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
    Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Order not found"));
    Order.OrderBuilder orderBuilder = existingOrder.toBuilder();

    if (requestDto.getMeals() == null && requestDto.getDrinks() == null) {
      throw new GeneralException("At least a drink or meal needs to be filled");
    }
    if (requestDto.getMeals() != null) {
      orderBuilder.meals(requestDto.getMeals().stream()
        .map(meal -> mealRepository.findById(meal.getId())
          .orElseThrow(() -> new RecordNotFoundException("Meal not found")))
        .toList());
    }
    else orderBuilder.meals(null);

    if (requestDto.getDrinks() != null) {
      orderBuilder.drinks(requestDto.getDrinks().stream()
        .map(drink -> drinkRepository.findById(drink.getId())
          .orElseThrow(() -> new RecordNotFoundException("Drink not found")))
        .toList());
    }
    else orderBuilder.drinks(null);

    if (requestDto.getDestination() != null) {
      orderBuilder.destination(locationRepository.findById(requestDto.getDestination().getId())
        .orElseThrow(() -> new RecordNotFoundException("Destination not found")));
    }
    else orderBuilder.destination(null);

    if (requestDto.getUser() != null) {
      orderBuilder.user(
        userRepository.findById(requestDto.getUser().getId()).orElseThrow(() -> new RecordNotFoundException("User not found")));
    }
    else orderBuilder.user(null);

    orderBuilder.orderDate(LocalDateTime.now());

    return orderRepository.save(orderBuilder.build());
  }

  public Order updateOrderFields(Integer id, OrderCompleteRequestDto requestDto) {
    Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Order not found"));
    Order.OrderBuilder orderBuilder = existingOrder.toBuilder();
    if (requestDto.getMeals() != null) {
      orderBuilder.meals(requestDto.getMeals().stream()
        .map(meal -> mealRepository.findById(meal.getId()).orElseThrow(RecordNotFoundException::new))
        .toList());
    }
    if (requestDto.getDrinks() != null) {
      orderBuilder.drinks(requestDto.getDrinks().stream()
        .map(drink -> drinkRepository.findById(drink.getId())
          .orElseThrow(RecordNotFoundException::new))
        .toList());
    }
    if (requestDto.getStatus() != null) {
      orderBuilder.status(requestDto.getStatus());
    }
    if (requestDto.getDestination() != null) {
      orderBuilder.destination(locationRepository.findById(requestDto.getDestination().getId())
        .orElseThrow(RecordNotFoundException::new));
    }
    if (requestDto.getUser() != null) {
      orderBuilder.user(userRepository.findById(requestDto.getUser().getId())
        .orElseThrow(RecordNotFoundException::new));
    }
    return orderRepository.save(orderBuilder.build());
  }

  // Perhaps I could be using something like this later as a helper method
  private <T, R> List<R> updateListOrKeepExisting(List<T> dtoList, List<R> existingList, Integer findId,
                                                  JpaRepository<R, Integer> repository) {
    if (dtoList != null) {
      return dtoList.stream()
        .map(dto -> repository.findById(findId).orElseThrow(RecordNotFoundException::new))
        .collect(Collectors.toList());
    }
    else {
      return existingList;
    }
  }

  public void deleteOrder(Integer id) {
    orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    orderRepository.deleteById(id);
  }
}

