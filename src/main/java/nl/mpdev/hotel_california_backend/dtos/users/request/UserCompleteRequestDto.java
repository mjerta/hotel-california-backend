package nl.mpdev.hotel_california_backend.dtos.users.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authorities.request.AuthorityCompleteRequestDto;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class UserCompleteRequestDto {
  private String username;
  private String password;
  private Set<AuthorityCompleteRequestDto> authority;
}
