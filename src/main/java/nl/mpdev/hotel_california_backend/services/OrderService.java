package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }
}
