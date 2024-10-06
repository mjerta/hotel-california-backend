package nl.mpdev.hotel_california_backend.dtos.authority.request;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class AuthorityCompleteRequestDto {
  private String authority;
}
