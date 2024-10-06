package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.mappers.user.UserCompleteMapper;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.services.UserService;
import org.apache.coyote.Response;
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
  private final UserCompleteMapper userCompleteMapper;

  public UserController(UserService userService, UserCompleteMapper userCompleteMapper) {
    this.userService = userService;
    this.userCompleteMapper = userCompleteMapper;
  }

  //controller restendpoints
  @PostMapping("/register")
  public ResponseEntity<UserCompleteResponseDto> registerNewUser(@Valid @RequestBody UserRegisterLimitedRequestDto requestDto) {
    User user = userService.registerNewUser(userCompleteMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userCompleteMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }
  @PostMapping("/customregister")
  public ResponseEntity<UserCompleteResponseDto> registerNewCustomUser(@Valid @RequestBody UserCompleteRequestDto requestDto) {
    User user = userService.registerNewCustomUser(userCompleteMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userCompleteMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

}
