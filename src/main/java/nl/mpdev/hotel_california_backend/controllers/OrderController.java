package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.orders.request.OrderCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.orders.response.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.orders.OrderCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Order;
import nl.mpdev.hotel_california_backend.services.OrderService;
import nl.mpdev.hotel_california_backend.services.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
  public ResponseEntity<OrderCompleteResponseDto> getOrderById(
    @AuthenticationPrincipal UserDetails userDetails,
    @PathVariable Integer id,
    @RequestParam(required = false) String orderReference) {
    String username = (userDetails != null) ? userDetails.getUsername() : null;
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(orderService.getOrderById(username, id, orderReference));
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

  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrder(@PathVariable Integer id,
                                                              @Valid @RequestBody OrderCompleteRequestDto requestDto) {
    Order order = orderService.updateOrder(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  // PATCH

  @PatchMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFields(@PathVariable Integer id,
                                                                    @Valid @RequestBody OrderCompleteRequestDto requestDto) {
    Order order = orderService.updateOrderFields(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  // DELETE

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable Integer id) {
    orderService.deleteOrder(id);
  }
}
