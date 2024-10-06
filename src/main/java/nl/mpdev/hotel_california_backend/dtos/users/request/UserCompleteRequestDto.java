package nl.mpdev.hotel_california_backend.dtos.users.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authority.AuthorityCompleteResponseDto;

import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class UserCompleteRequestDto {
  private Integer id;
  private String username;
  private Set<AuthorityCompleteResponseDto> authority;
}
