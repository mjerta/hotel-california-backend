package nl.mpdev.hotel_california_backend.dtos.users.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;

@Builder(toBuilder = true)
@Getter
public class UserRegisterLimitedRequestDto {
  @NotEmpty(message = "Username cannot be empty")
  @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
  private String username;
  @NotEmpty(message = "Password cannot be empty")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;
  private ProfileCompleteRequestDto profile;
}
