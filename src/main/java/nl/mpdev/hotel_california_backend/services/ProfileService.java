package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private final ProfileRepository profileRepository;

  public ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }
}
