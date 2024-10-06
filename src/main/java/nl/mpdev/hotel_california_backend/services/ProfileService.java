package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

  private final ProfileRepository profileRepository;

  public ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  public Profile getProfileById(Integer id) {
    return profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
  }

  public List<Profile> getProfiles() {
    return profileRepository.findAll();
  }

  public Profile addProfile(Profile entity) {
    return profileRepository.save(entity);
  }

  public Profile updateProfile(Integer id, ProfileCompleteRequestDto requestDto) {
    Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    existingProfile = existingProfile.toBuilder()
      .firstName(requestDto.getFirstName())
      .lastName(requestDto.getLastName())
      .phoneNumber(requestDto.getPhoneNumber())
      .address(requestDto.getAddress())
      .points((requestDto.getPoints()))
      .build();
    return profileRepository.save(existingProfile);
  }
}
