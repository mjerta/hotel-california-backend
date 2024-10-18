package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.mpdev.hotel_california_backend.dtos.locations.response.LocationCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.locations.LocationCompleteMapper;
import nl.mpdev.hotel_california_backend.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Location endpoints")
public class LocationController {

  private final LocationService locationService;
  private final LocationCompleteMapper locationCompleteMapper;

  public LocationController(LocationService locationService, LocationCompleteMapper locationCompleteMapper) {
    this.locationService = locationService;
    this.locationCompleteMapper = locationCompleteMapper;
  }

  @Operation(summary = "public", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a list of locatiosn with a complete vie of the entity")
  @GetMapping("")
  public ResponseEntity<List<LocationCompleteResponseDto>> getLocations() {
    List<LocationCompleteResponseDto> locations = locationService.getLocations().stream().map(locationCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(locations);
  }


}
