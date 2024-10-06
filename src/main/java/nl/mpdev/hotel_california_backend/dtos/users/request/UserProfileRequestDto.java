package nl.mpdev.hotel_california_backend.dtos.users.request;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileIdRequestDto;

@Builder(toBuilder = true)
@Getter
public class UserProfileRequestDto {
  private Integer id;
  private ProfileIdRequestDto profile;
}
