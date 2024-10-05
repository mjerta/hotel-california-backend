package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.orders.OrderCompleteMapper;
import nl.mpdev.hotel_california_backend.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;
  private final OrderCompleteMapper orderCompleteMapper;

  public OrderController(OrderService orderService, OrderCompleteMapper orderCompleteMapper) {
    this.orderService = orderService;
    this.orderCompleteMapper = orderCompleteMapper;
  }

  // GET

  @GetMapping("{id}")
  public ResponseEntity<OrderCompleteResponseDto> getOrderById(@PathVariable Integer id) {
    var test = orderService.getOrderById(id);
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(test);
    return ResponseEntity.ok().body(responseDto);
  }
}
