package nl.mpdev.hotel_california_backend.dtos.authorities.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityCompleteRequestDto {
  private String authority;
}
