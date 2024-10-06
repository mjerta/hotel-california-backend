package nl.mpdev.hotel_california_backend.dtos.users.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authority.request.AuthorityCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.authority.response.AuthorityCompleteResponseDto;

import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class UserCompleteRequestDto {
  private Integer id;
  private String username;
  private String password;
  private Set<AuthorityCompleteRequestDto> authority;
}
