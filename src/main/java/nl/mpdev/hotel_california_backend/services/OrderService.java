package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order getOrderById(Integer id) {
    return orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

}
