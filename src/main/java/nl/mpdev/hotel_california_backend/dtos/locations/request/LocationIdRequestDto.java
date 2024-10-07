package nl.mpdev.hotel_california_backend.dtos.locations.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class LocationIdRequestDto {
  private Integer id;
}
