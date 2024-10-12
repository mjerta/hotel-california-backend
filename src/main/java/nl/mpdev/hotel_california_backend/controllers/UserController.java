package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserLoginRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.mappers.users.UserCompleteMapper;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;
  private final UserCompleteMapper userCompleteMapper;

  public UserController(UserService userService, UserCompleteMapper userCompleteMapper) {
    this.userService = userService;
    this.userCompleteMapper = userCompleteMapper;
  }

  // GET

  @Operation(summary = "ROLE_STAFF", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a list of users with a complete view of the entity")
  @GetMapping("/users")
  public ResponseEntity<List<UserCompleteResponseDto>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers().stream().map(userCompleteMapper::toDto).toList());
  }

  //POST

  @Operation(summary = "public" , description = "Send a post request with a json object with a limited view of a user. The default user role of USER will be added to this user.")
  @ApiResponse(responseCode = "201", description = "Returns a single object of the user that's being added with a complete view")
  @PostMapping("/register")
  public ResponseEntity<UserCompleteResponseDto> registerNewUser(@Valid @RequestBody UserRegisterLimitedRequestDto requestDto) {
    User user = userService.registerNewUser(userCompleteMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userCompleteMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @Operation(summary = "public" , description = "Send a post request with a json object with a complete view of a user. This will have the ability to add roles like USER, ADMIN, MANAGER, SUPERADMIN")
  @ApiResponse(responseCode = "201", description = "Returns a single object of the user that's being added with a complete view")
  @PostMapping("/customregister")
  public ResponseEntity<UserCompleteResponseDto> registerNewCustomUser(@Valid @RequestBody UserCompleteRequestDto requestDto) {
    User user = userService.registerNewCustomUser(userCompleteMapper.toEntity(requestDto));
    UserCompleteResponseDto responseDto = userCompleteMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @Operation(summary = "public" , description = "Send a post request with a json object with a login view of a user.")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the JWT token belongs to the user. This could be saved on local storage. ")
  @PostMapping(value = "/login")
  public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
    return ResponseEntity.ok().body(userService.verify(userCompleteMapper.toEntity(requestDto)));
  }

  // DELETE

  @Operation(summary = "ROLE_MANAGER" , description = "Send a delete request. The user will be verified based on the jwt token and the corresponded user will be selected")
  @ApiResponse(responseCode = "204", description = "Returns the value void")
  @DeleteMapping("/users/loggeduser")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUserByUserLoggedIn() {
    userService.deleteUserByUserLoggedIn();
  }
}
