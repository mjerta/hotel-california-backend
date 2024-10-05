package nl.mpdev.hotel_california_backend.mappers.locations;

import nl.mpdev.hotel_california_backend.dtos.locations.LocationCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.locations.LocationIdRequestDto;
import nl.mpdev.hotel_california_backend.models.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationCompleteMapper {

  public Location toEntity(LocationIdRequestDto dto) {
    return Location.builder()
      .id(dto.getId())
      .build();
  }

  public LocationCompleteResponseDto toDto(Location entity) {
    return LocationCompleteResponseDto.builder()
      .id(entity.getId())
      .locationNumber(entity.getLocationNumber())
      .isOccupied(entity.getIsOccupied())
      .locationType(entity.getLocationType().getType())
      .build();
  }



}
