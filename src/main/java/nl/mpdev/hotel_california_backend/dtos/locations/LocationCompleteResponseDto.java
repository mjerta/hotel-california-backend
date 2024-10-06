package nl.mpdev.hotel_california_backend.dtos.locations;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class LocationCompleteResponseDto {
  private Integer id;
  private Integer locationNumber;
  private Boolean isOccupied;
  private String locationType;
}
