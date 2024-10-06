package nl.mpdev.hotel_california_backend.mappers.user;

import nl.mpdev.hotel_california_backend.dtos.users.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.UserIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserLimitedMapper {

  public User toEntity(UserIdRequestDto requestDto) {
    if(requestDto == null) return null;
    return User.builder()
      .id(requestDto.getId())
      .build();
  }

  public User toEntity(UserRegisterLimitedRequestDto requestDto) {
    if(requestDto == null) return null;
    return User.builder()
      .username(requestDto.getUsername())
      .password(requestDto.getPassword())
      .build();
  }

  public UserCompleteResponseDto toDto(User entity) {
    if(entity == null) return null;
    return UserCompleteResponseDto.builder()

      .id(entity.getId())
      .username(entity.getUsername())
      .build();
  }
}
