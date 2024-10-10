package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

  private final ServiceHelper serviceHelper;
  private final ProfileRepository profileRepository;
  private final DrinkRepository drinkRepository;

  public ProfileService(ServiceHelper serviceHelper, ProfileRepository profileRepository, DrinkRepository drinkRepository) {
    this.serviceHelper = serviceHelper;
    this.profileRepository = profileRepository;
    this.drinkRepository = drinkRepository;
  }

  public Profile getProfileById(Integer id) {
    Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No profile is found"));


    return existingProfile;
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

  public Profile updateProfileFields(Integer id, ProfileCompleteRequestDto requestDto) {
    Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException());
    serviceHelper.setFieldsIfNotNUll(existingProfile, requestDto);
    return profileRepository.save(existingProfile);
  }

  public void deleteProfile(Integer id) {
    profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(""));
    profileRepository.deleteById(id);
  }
}
