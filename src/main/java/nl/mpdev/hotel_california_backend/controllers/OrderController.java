package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.Authenticator;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;
  private final OrderCompleteMapper orderCompleteMapper;

  public OrderController(OrderService orderService, OrderCompleteMapper orderCompleteMapper) {
    this.orderService = orderService;
    this.orderCompleteMapper = orderCompleteMapper;
  }

  // GET

  @Operation(summary = "ROLE_USER", description = "Send a get request with an id")
  @ApiResponse(responseCode = "200", description = "Returns a order with a complete view of the entity")
  @GetMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> getOrderByIdByUserLoggedIn(@PathVariable Integer id) {
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(orderService.getOrderByIdByUserLoggedIn(id));
    return ResponseEntity.ok().body(responseDto);
  }


  @Operation(summary = "public", description = "Send a get request with an orderreference")
  @ApiResponse(responseCode = "200", description = "Returns a order with a complete view of the entity")
  @GetMapping("/orderreference")
  public ResponseEntity<OrderCompleteResponseDto> getOrderByOrderReference(@RequestParam String orderReference) {
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(orderService.getOrderByOrderReference(orderReference));
    return ResponseEntity.ok().body(responseDto);
  }

  @Operation(summary = "ROLE_STAFF", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a list of orders with a complete view of the entity")
  @GetMapping("")
  public ResponseEntity<List<OrderCompleteResponseDto>> getOrders() {
    List<OrderCompleteResponseDto> orders = orderService.getOrders().stream().map(orderCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(orders);
  }

  // POST

  @Operation(summary = "public" , description = "Send a post request with a json object with a 'new order' view of a order. If a user is not logged in the client should keep a cookie of the orderrefence to send it with any other request. If a user is logged in , the jtw token is verified and data is being inserted including the user.")
  @ApiResponse(responseCode = "201", description = "Returns a single object of the order that's being added with a complete view")
  @PostMapping("")
  public ResponseEntity<OrderCompleteResponseDto> addOrder(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody OrderNewRequestDto requestDto) {
    Order order = orderService.addOrder(orderCompleteMapper.toEntity(requestDto));
    OrderCompleteResponseDto responseDto = orderCompleteMapper.toDto(order);

    URI uri;
    if(userDetails == null) {
      uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/orderrefence").toUriString());
    } else {
      uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/").toUriString());
    }
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT

  @Operation(summary = "ROLE_USER" , description = "Send a put request with an id and a json object with a update view of a order, empty properties will be set null. The user will be verified to belong with this order based on the jwt token")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PutMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByUserLoggedIn(@PathVariable Integer id,
                                                                            @Valid @RequestBody OrderUpdateRequestDto requestDto) {
    Order order = orderService.updateOrderByUserLoggedIn(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }
  @Operation(summary = "public" , description = "Send a put request with an order-reference and a json object with a update view of a order, empty properties will be set null. The order will be retrieved based on the orderreference thats given.")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PutMapping("/orderreference")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByOrderReference(@Valid @RequestBody OrderUpdateRequestDto requestDto,
                                                                              @RequestParam String orderReference) {
    Order order = orderService.updateOrderByOrderReference(orderReference, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }
  @Operation(summary = "ROLE_STAFF" , description = "Send a put request with an id and a json object with a complete 'complete-staff' view of a order, empty properties will be set null")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PutMapping("/updateorderbystaff/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderByStaff(@PathVariable Integer id,
                                                                            @Valid @RequestBody OrderCompleteStaffRequestDto requestDto) {
    Order order = orderService.updateOrderByStaff(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }
  // PATCH

  @Operation(summary = "ROLE_USER" , description = "Send a patch request with and id and a json object with a update view of a order, empty properties will beholds its original value. The user will be verified to belong with this order based on the jwt token")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PatchMapping("/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFieldsByUserLoggedIn(@PathVariable Integer id,
                                                                                  @Valid @RequestBody OrderUpdateRequestDto requestDto) {
    Order order = orderService.updateOrderFieldsByUserLoggedIn(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  @Operation(summary = "public" , description = "Send a patch request with and order-reference and a json object with a update view of a order, empty properties will beholds its original value. The order will be retrieved based on the orderreference thats given.")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PatchMapping("/orderreference")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFieldsByOrderReference(
    @Valid @RequestBody OrderUpdateRequestDto requestDto,
    @RequestParam String orderReference
  ) {
    Order order = orderService.updateOrderFieldsByOrderReference(orderReference, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }

  @Operation(summary = "ROLE_STAFF", description = "Send a patch request with and id and a json object with a complete 'complete-staff' view of a order, empty properties will not be overwritten")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PatchMapping("/updateorderbystaff/{id}")
  public ResponseEntity<OrderCompleteResponseDto> updateOrderFieldsByStaff(@PathVariable Integer id,
                                                                           @Valid @RequestBody OrderCompleteStaffRequestDto requestDto) {
    Order order = orderService.updateOrderFieldsByStaff(id, requestDto);
    return ResponseEntity.ok().body(orderCompleteMapper.toDto(order));
  }
  @Operation(summary = "ROLE_STAFF" , description = "Send a delete request with an id")
  @ApiResponse(responseCode = "204", description = "Returns the value void")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable Integer id) {
    orderService.deleteOrder(id);
  }
}
