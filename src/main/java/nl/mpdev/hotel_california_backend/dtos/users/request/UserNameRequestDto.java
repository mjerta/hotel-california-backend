package nl.mpdev.hotel_california_backend.dtos.users.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class UserNameRequestDto {
  @NotBlank(message = "Username must not be null")
  private String username;
}
