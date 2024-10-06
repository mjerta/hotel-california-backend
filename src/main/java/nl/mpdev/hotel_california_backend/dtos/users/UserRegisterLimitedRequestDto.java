package nl.mpdev.hotel_california_backend.dtos.users;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class UserRegisterLimitedRequestDto {
  private String username;
  private String password;
}
