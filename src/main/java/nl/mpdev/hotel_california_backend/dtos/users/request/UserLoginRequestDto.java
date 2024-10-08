package nl.mpdev.hotel_california_backend.dtos.users.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class UserLoginRequestDto {
  @NotBlank(message = "Username is not entered")
  public String username;
  @NotBlank(message = "Username is not entered")
  public String password;
}
