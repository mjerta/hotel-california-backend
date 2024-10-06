package nl.mpdev.hotel_california_backend.dtos.users.response;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.models.Profile;

@Builder(toBuilder = true)
@Getter
public class UserProfileResponseDto {
  private Integer id;
  private String username;
  private Profile profile;
}
