package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserLoginRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserProfileRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserProfileResponseDto;
import nl.mpdev.hotel_california_backend.mappers.users.UserCompleteMapper;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;
  private final UserCompleteMapper userCompleteMapper;

  public UserController(UserService userService, UserCompleteMapper userCompleteMapper) {
    this.userService = userService;
    this.userCompleteMapper = userCompleteMapper;
  }

  // controller rest-end-points

  // GET

  @GetMapping("/users")
  public ResponseEntity<List<UserCompleteResponseDto>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers().stream().map(userCompleteMapper::toDto).toList());
  }

  //POST

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

  @PostMapping(value = "/login")
  public ResponseEntity<String> login(@Valid @RequestBody User user) {
    return ResponseEntity.ok().body(userService.verify(user));
  }


  // PATCH

//  @PatchMapping("/userprofiles")
//  public ResponseEntity<UserProfileResponseDto> updateProfileFields(
//                                                       @Valid @RequestBody UserProfileRequestDto requestDto) {
//    User order = userService.updateProfileFields(requestDto);
//    return ResponseEntity.ok().body(userCompleteMapper.toUserProfileResponse(order));
//  }

}
