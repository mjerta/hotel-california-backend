package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderCompleteStaffRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderUpdateRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderNewRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.response.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.orders.OrderCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

  @GetMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> getOrderByIdByUserLoggedIn(@PathVariable Integer id) {
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(orderService.getOrderByIdByUserLoggedIn(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("/orderrefence")
  public ResponseEntity<OrderCompleteResponseDto> getOrderByOrderReference(@RequestParam String orderReference) {
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(orderService.getOrderByOrderReference(orderReference));
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("")
  public ResponseEntity<List<OrderCompleteResponseDto>> getOrders() {
    List<OrderCompleteResponseDto> orders = orderService.getOrders().stream().map(orderCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(orders);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<OrderCompleteResponseDto> addOrder(@Valid @RequestBody OrderNewRequestDto requestDto) {
    Order order = orderService.addOrder(orderCompleteMapper.toEntity(requestDto));
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(order);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByUserLoggedIn(@PathVariable Integer id,
                                                                            @Valid @RequestBody OrderUpdateRequestDto requestDto) {
    Order order = orderService.updateOrderByUserLoggedIn(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  @PutMapping("/orderreference")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByOrderReference(@Valid @RequestBody OrderUpdateRequestDto requestDto,
                                                                              @RequestParam String orderReference) {
    Order order = orderService.updateOrderByOrderReference(orderReference, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  @PutMapping("/updateorderbystaff/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByStaff(@PathVariable Integer id,
                                                                            @Valid @RequestBody OrderCompleteStaffRequestDto requestDto) {
    Order order = orderService.updateOrderByStaff(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }
  // PATCH

  @PatchMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFieldsByUserLoggedIn(@PathVariable Integer id,
                                                                                  @Valid @RequestBody OrderUpdateRequestDto requestDto) {
    Order order = orderService.updateOrderFieldsByUserLoggedIn(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  @PatchMapping("/orderreference")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFieldsByOrderReference(
    @Valid @RequestBody OrderUpdateRequestDto requestDto,
    @RequestParam String orderReference
  ) {
    Order order = orderService.updateOrderFieldsByOrderReference(orderReference, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  // DELETE

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable Integer id) {
    orderService.deleteOrder(id);
  }
}
