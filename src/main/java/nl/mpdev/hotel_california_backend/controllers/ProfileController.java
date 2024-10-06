package nl.mpdev.hotel_california_backend.controllers;

import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteResponseDto;
import nl.mpdev.hotel_california_backend.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/profiles")
public class ProfileController {

  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  // GET

//  @GetMapping("{id")
//  public ResponseEntity<ProfileCompleteResponseDto> getProfileById(@PathVariable Integer id) {
//
//    return ResponseEntity.ok().body(Object);
//  }

}
