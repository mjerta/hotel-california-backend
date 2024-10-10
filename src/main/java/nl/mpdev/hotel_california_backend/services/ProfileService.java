package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.DrinkRepository;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

  private final ServiceHelper serviceHelper;
  private final ProfileRepository profileRepository;
  private final DrinkRepository drinkRepository;
  private final UserRepository userRepository;

  public ProfileService(ServiceHelper serviceHelper, ProfileRepository profileRepository, DrinkRepository drinkRepository,
                        UserRepository userRepository) {
    this.serviceHelper = serviceHelper;
    this.profileRepository = profileRepository;
    this.drinkRepository = drinkRepository;
    this.userRepository = userRepository;
  }

  public Profile getProfileById(Integer id) {
    Profile existingProfile = profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No profile is found"));
    Profile valdateProfile = getProfileByUser(existingProfile);
    if (valdateProfile != null) return valdateProfile;
    throw new GeneralException("No profile found with logged in user");
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

  private Profile getProfileByUser(Profile existingProfile) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User userToCheck = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RecordNotFoundException("No user found"));
      if (existingProfile.getUser() == null) {
        throw new GeneralException("There is no profile associated with the user");
      }
      if (existingProfile.getUser().getUsername().equals(userToCheck.getUsername())) {
        return existingProfile;
      }
    }
    return null;
  }
}


