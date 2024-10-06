package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.services.ProfileService;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }
}
