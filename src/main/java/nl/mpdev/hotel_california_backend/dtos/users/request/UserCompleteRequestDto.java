package nl.mpdev.hotel_california_backend.dtos.users.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authorities.request.AuthorityCompleteRequestDto;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class UserCompleteRequestDto {
  private String username;
  private String password;
  @NotEmpty(message = "Authority set cannot be empty")
  private Set<AuthorityCompleteRequestDto> authority;
}
