package nl.mpdev.hotel_california_backend.dtos.profiles.request;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ProfileIdRequestDto {
  private Integer id;
}
