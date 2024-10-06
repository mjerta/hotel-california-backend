package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.mappers.user.UserLimitedMapper;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;
  private final UserLimitedMapper userLimitedMapper;

  public UserController(UserService userService, UserLimitedMapper userLimitedMapper) {
    this.userService = userService;
    this.userLimitedMapper = userLimitedMapper;
  }

  //controller restendpoints
  @PostMapping("/register")
  public ResponseEntity<UserCompleteResponseDto> registerNewUser(@Valid @RequestBody UserRegisterLimitedRequestDto requestDto) {
    User user = userService.registerNewUser(userLimitedMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userLimitedMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }
}
