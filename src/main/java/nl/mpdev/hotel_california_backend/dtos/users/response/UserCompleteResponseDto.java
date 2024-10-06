package nl.mpdev.hotel_california_backend.dtos.users.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authorities.response.AuthorityCompleteResponseDto;

import java.util.Set;

@Builder
@Getter
public class UserCompleteResponseDto {
  private Integer id;
  private String username;
  private Set<AuthorityCompleteResponseDto> authority;
}
