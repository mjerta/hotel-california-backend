package nl.mpdev.hotel_california_backend.dtos.users.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class UserLimitedResponseDto {
  private Integer id;
  private String username;
}
