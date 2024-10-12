package nl.mpdev.hotel_california_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.response.ProfileCompleteResponseDto;
import nl.mpdev.hotel_california_backend.mappers.profiles.ProfileCompleteMapper;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

  private final ProfileService profileService;
  private final ProfileCompleteMapper profileCompleteMapper;

  public ProfileController(ProfileService profileService, ProfileCompleteMapper profileCompleteMapper) {
    this.profileService = profileService;
    this.profileCompleteMapper = profileCompleteMapper;
  }

  //  GET

  @Operation(summary = "ROLE_USER", description = "Send an get request, data will be retrieved based on user jwt token verified on the backend")
  @ApiResponse(responseCode = "200", description = "Returns a profile with a complete view of the entity")
  @GetMapping("/loggeduser")
  public ResponseEntity<ProfileCompleteResponseDto> getProfileByUserLoggedIn() {
    ProfileCompleteResponseDto responseDto = profileCompleteMapper.toDto(profileService.getProfileByUserLoggedIn());
    return ResponseEntity.ok().body(responseDto);
  }

  @Operation(summary = "ROLE_MANAGER", description = "Send a get request")
  @ApiResponse(responseCode = "200", description = "Returns a profile with a complete view of the entity")
  @GetMapping("")

  public ResponseEntity<List<ProfileCompleteResponseDto>> getProfiles() {
    List<ProfileCompleteResponseDto> profiles = profileService.getProfiles().stream().map(profileCompleteMapper::toDto).toList();
    return ResponseEntity.ok().body(profiles);
  }

  // PUT

  @Operation(summary = "ROLE_USER" , description = "Send a put request with a json object with a complete view of a order, empty properties will be set null, profile will be selected based on the jwt token verified on the backend")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PutMapping("")
  public ResponseEntity<ProfileCompleteResponseDto> updateProfile(@Valid @RequestBody ProfileCompleteRequestDto requestDto) {
    Profile profile = profileService.updateProfile(requestDto);
    return ResponseEntity.ok().body(profileCompleteMapper.toDto(profile));
  }

  // PATCH

  @Operation(summary = "ROLE_USER" , description = "Send a patch request with a json object with a complete view of a order, empty properties will be will beholds its original value, profile will be selected based on the jwt token verified on the backend")
  @ApiResponse(responseCode = "200", description = "Returns a single object of the order that's has been updated with a complete view")
  @PatchMapping("")
  public ResponseEntity<ProfileCompleteResponseDto> updateProfileFields(@Valid @RequestBody ProfileCompleteRequestDto requestDto) {
    Profile profile = profileService.updateProfileFields(requestDto);
    return ResponseEntity.ok().body(profileCompleteMapper.toDto(profile));
  }

  // DELETE

  @Operation(summary = "ROLE_MANAGER" , description = "Send a delete request with an id")
  @ApiResponse(responseCode = "204", description = "Returns the value void")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfile(@PathVariable Integer id) {
    profileService.deleteProfile(id);
  }

}
