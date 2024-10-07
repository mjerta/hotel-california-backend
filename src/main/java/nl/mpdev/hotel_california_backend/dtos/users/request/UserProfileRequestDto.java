package nl.mpdev.hotel_california_backend.dtos.users.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileIdRequestDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDto {
  private ProfileIdRequestDto profile;
}
