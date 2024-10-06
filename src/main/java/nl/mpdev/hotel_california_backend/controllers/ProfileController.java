package nl.mpdev.hotel_california_backend.controllers;

import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.drinks.DrinkCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.profiles.ProfileCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Drink;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/v1/profiles")
public class ProfileController {

  private final ProfileService profileService;
  private final ProfileCompleteMapper profileCompleteMapper;

  public ProfileController(ProfileService profileService, ProfileCompleteMapper profileCompleteMapper) {
    this.profileService = profileService;
    this.profileCompleteMapper = profileCompleteMapper;
  }

  //  GET

  @GetMapping("{id}")
  public ResponseEntity<ProfileCompleteResponseDto> getProfileById(@PathVariable Integer id) {
    ProfileCompleteResponseDto responseDto = profileCompleteMapper.toDto(profileService.getProfileById(id));
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("")
  public ResponseEntity<List<ProfileCompleteResponseDto>> getProfiles() {
    List<ProfileCompleteResponseDto> profiles = profileService.getProfiles().stream().map(profileCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(profiles);
  }

  // POST

  @PostMapping("")
  public ResponseEntity<ProfileCompleteResponseDto> addProfile(@Valid @RequestBody ProfileCompleteRequestDto requestDto) {
    Profile profile = profileService.addProfile(profileCompleteMapper.toEntity(requestDto));
    ProfileCompleteResponseDto responseDto = profileCompleteMapper.toDto(profile);
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + responseDto.getId()).toUriString());
    return ResponseEntity.created(uri).body(responseDto);
  }

  // PUT

  @PutMapping("/{id}")
  public ResponseEntity<ProfileCompleteResponseDto> updateProfile(@PathVariable Integer id, @Valid @RequestBody ProfileCompleteRequestDto requestDto) {
    Profile profile = profileService.updateProfile(id, requestDto);
    return ResponseEntity.ok().body(profileCompleteMapper.toDto(profile));
  }

  // PATCH

  @PatchMapping("/{id}")
  public ResponseEntity<ProfileCompleteResponseDto> updateProfileFields(@PathVariable Integer id, @Valid @RequestBody ProfileCompleteRequestDto requestDto) {
    Profile profile = profileService.updateProfileFields(id, requestDto);
    return ResponseEntity.ok().body(profileCompleteMapper.toDto(profile));
  }


}
