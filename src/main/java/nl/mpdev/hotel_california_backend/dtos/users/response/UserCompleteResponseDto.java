package nl.mpdev.hotel_california_backend.dtos.users.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authorities.response.AuthorityCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.response.ProfileCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Profile;

import java.util.Set;

@Builder
@Getter
public class UserCompleteResponseDto {
  private String username;
  private Set<AuthorityCompleteResponseDto> authority;
  private ProfileCompleteResponseDto profile;
}
