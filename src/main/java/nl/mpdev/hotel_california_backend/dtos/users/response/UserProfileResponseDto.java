package nl.mpdev.hotel_california_backend.dtos.users.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.profiles.response.ProfileCompleteResponseDto;

@Builder(toBuilder = true)
@Getter
public class UserProfileResponseDto {
  private String username;
  private ProfileCompleteResponseDto profile;
}
