package nl.mpdev.hotel_california_backend.dtos.authorities.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class AuthorityCompleteResponseDto {
  private String username;
  private String authority;
}
