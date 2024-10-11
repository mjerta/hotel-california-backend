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

  public Profile getProfileByUserLoggedIn() {
    Profile valdateProfile = getProfileByUser();
    if (valdateProfile != null) return valdateProfile;
    throw new GeneralException("No profile found with logged in user");
  }

  public List<Profile> getProfiles() {
    return profileRepository.findAll();
  }

  public Profile updateProfile(ProfileCompleteRequestDto requestDto) {

    Profile existingProfile = getProfileByUser();
    Profile.ProfileBuilder profileBuilder = existingProfile.toBuilder();
    if(requestDto.getFirstName() != null) {
      profileBuilder.firstName(requestDto.getFirstName());
    }
    if(requestDto.getLastName() != null) {
      profileBuilder.lastName(requestDto.getLastName());
    }
    if(requestDto.getPhoneNumber() != null) {
      profileBuilder.phoneNumber(requestDto.getPhoneNumber());
    }
    if(requestDto.getAddress() != null) {
      profileBuilder.address(requestDto.getAddress());
    }
    if(requestDto.getPoints() != null) {
      profileBuilder.points(requestDto.getPoints());
    }
    return profileRepository.save(profileBuilder.build());
  }

  public Profile updateProfileFields(ProfileCompleteRequestDto requestDto) {
    Profile existingProfile = getProfileByUser();
    serviceHelper.setFieldsIfNotNUll(existingProfile, requestDto);
    return profileRepository.save(existingProfile);
  }

  public void deleteProfile(Integer id) {
    profileRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(""));
    profileRepository.deleteById(id);
  }

  private Profile getProfileByUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User userToCheck = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RecordNotFoundException("No user found"));
      if (userToCheck.getProfile() == null) {
        throw new GeneralException("There is no profile associated with the user");
      }
      return userToCheck.getProfile();
    }
    return null;
  }
}


