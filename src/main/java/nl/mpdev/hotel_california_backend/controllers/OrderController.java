package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.orders.OrderCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

  @GetMapping("")
  public ResponseEntity<List<OrderCompleteResponseDto>> getOrders() {
    List<OrderCompleteResponseDto> orders = orderService.getOrders().stream().map(orderCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(orders);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<OrderCompleteResponseDto> addOrder(@Valid @RequestBody OrderCompleteRequestDto requestDto) {
    Order order = orderService.addOrder(orderCompleteMapper.toEntity(requestDto));
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(order);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

}
