package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.mappers.user.UserLimitedMapper;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;
  private final UserLimitedMapper userLimitedMapper;

  public UserController(UserService userService, UserLimitedMapper userLimitedMapper) {
    this.userService = userService;
    this.userLimitedMapper = userLimitedMapper;
  }

  @PostMapping("/register")
  public ResponseEntity<UserCompleteResponseDto> registerNewUser(@Valid @RequestBody UserRegisterLimitedRequestDto requestDto) {
    User user = userService.registerNewUser(userLimitedMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userLimitedMapper.toDto(user);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

}
