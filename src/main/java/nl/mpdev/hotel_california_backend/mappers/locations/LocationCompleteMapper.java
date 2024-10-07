package nl.mpdev.hotel_california_backend.mappers.locations;

import nl.mpdev.hotel_california_backend.dtos.locations.response.LocationCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.locations.request.LocationIdRequestDto;
import nl.mpdev.hotel_california_backend.models.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationCompleteMapper {

  public Location toEntity(LocationIdRequestDto dto) {
    if(dto == null) {
      return null;
    }
    return Location.builder()
      .id(dto.getId())
      .build();
  }

  public LocationCompleteResponseDto toDto(Location entity) {
    if(entity == null) {
      return null;
    }
    return LocationCompleteResponseDto.builder()
      .id(entity.getId())
      .locationNumber(entity.getLocationNumber())
      .isOccupied(entity.getIsOccupied())
      .locationType(entity.getLocationType().getType())
      .build();
  }



}
