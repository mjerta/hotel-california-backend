package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.models.Location;
import nl.mpdev.hotel_california_backend.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

  private final LocationRepository locationRepository;

  public LocationService(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public List<Location> getLocations() { return locationRepository.findAll(); }
}
