package nl.mpdev.hotel_california_backend.service;

import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.helpers.ServiceHelper;
import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import nl.mpdev.hotel_california_backend.services.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

  @Mock
  ProfileRepository profileRepository;

  @Mock
  UserRepository userRepository;

  @Spy
  private ServiceHelper serviceHelper;

  @InjectMocks
  ProfileService profileService;

  @BeforeEach
  void setUp() {
    // Set up mocked authentication
    UserDetails userDetails = mock(UserDetails.class);
    lenient().when(userDetails.getUsername()).thenReturn("mjerta");
    Authentication authentication = mock(Authentication.class);
    lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn_succes - testing if GET method for profile based on successful authenticated")
  void getProfileByUserLoggedIn_succes() {
    // Arrange
    Integer id = 125;
    Profile expectedProfile = Profile.builder()
      .id(id)
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();
    User user = User.builder()
      .profile(expectedProfile)
      .orders(null)
      .username("mjerta")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_USER").username("mjerta").build()))
      .enabled(true)
      .build();

    expectedProfile = expectedProfile.toBuilder().
      user(user)
      .build();

    user = user.toBuilder().profile(expectedProfile).build();

    // Mock userRepository to return the user with the profile
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));


    // Act
    Profile result = profileService.getProfileByUserLoggedIn();

    // Assert
    assertEquals(expectedProfile.getId(), result.getId());

  }

  @Test
  @DisplayName("getProfiles - testing GET method  to get all profiles")
  void getProfiles() {
    // Arrange

    Integer profileId1 = 125;
    Profile profile1 = Profile.builder()
      .id(profileId1)
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();
    User user1 = User.builder()
      .profile(profile1)
      .orders(null)
      .username("mjerta")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_USER").username("mjerta").build()))
      .enabled(true)
      .build();

    profile1 = profile1.toBuilder()
      .user(user1)
      .build();


    Integer profileId2 = 129;
    Profile profile2 = Profile.builder()
      .id(profileId2)
      .firstName("Piet")
      .lastName("Postma")
      .phoneNumber("0612345679")
      .address("Klaverstraat")
      .points(200)
      .build();
    User user2 = User.builder()
      .profile(profile2)
      .orders(null)
      .username("piet")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_MANAGER").username("Piet").build()))
      .enabled(true)
      .build();

    profile2 = profile2.toBuilder()
      .user(user2)
      .build();


    when(profileRepository.findAll()).thenReturn(List.of(profile1, profile2));

    // Act
    List<Profile> result = profileService.getProfiles();

    // Assert
    assertEquals(2, result.size());

    // First profile
    Profile profileResult1 = result.get(0);
    assertEquals(profileId1, profileResult1.getId());
    assertEquals("Maarten", profileResult1.getFirstName());
    assertEquals("Postma", profileResult1.getLastName());
    assertEquals("0612345678", profileResult1.getPhoneNumber());
    assertEquals("JanKlaaseStraat", profileResult1.getAddress());
    assertEquals(130, profileResult1.getPoints());

    // Testing the relation
    assertEquals("mjerta", profileResult1.getUser().getUsername());
    // Testing the other side of the relation
    assertEquals("Maarten", profileResult1.getUser().getProfile().getFirstName());

    // Second profile
    Profile profileResult2 = result.get(1);
    assertEquals(profileId2, profileResult2.getId());
    assertEquals("Piet", profileResult2.getFirstName());
    assertEquals("Postma", profileResult2.getLastName());
    assertEquals("0612345679", profileResult2.getPhoneNumber());
    assertEquals("Klaverstraat", profileResult2.getAddress());
    assertEquals(200, profileResult2.getPoints());

    // testing the relation
    assertEquals("piet", profileResult2.getUser().getUsername());
    // Testing the other side of the relation
    assertEquals("Piet", profileResult2.getUser().getProfile().getFirstName());




  }

  @Test
  @DisplayName("updateProfileFields - testing the PATCH update")
  void updateProfileFields() {
    // Arrange
    Integer id = 1;
    Profile existingProfile = Profile.builder()
      .id(id)
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();

    User user = User.builder()
      .profile(existingProfile)
      .orders(null)
      .username("mjerta")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_USER").username("mjerta").build()))
      .enabled(true)
      .build();

    ProfileCompleteRequestDto requestDto = ProfileCompleteRequestDto.builder()
      .firstName("Johan")
      .lastName(null) // Nullable field to test `setFieldsIfNotNull` logic
      .phoneNumber("0612345676")
      .address("Matenstraat")
      .points(30)
      .build();

    // Act
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
    when(profileRepository.save(any(Profile.class))).thenReturn(existingProfile);
    // Act
    Profile updatedProfile = profileService.updateProfileFields(requestDto);

    // Assert

    assertEquals("Johan", updatedProfile.getFirstName());
    assertEquals("Postma", updatedProfile.getLastName());
    assertEquals("0612345676", updatedProfile.getPhoneNumber());
    assertEquals("Matenstraat", updatedProfile.getAddress());
    assertEquals(30,updatedProfile.getPoints());

    verify(serviceHelper).setFieldsIfNotNUll(existingProfile, requestDto);
  }

  @Test
  @DisplayName("deleteProfile - testing the DELETE endpoint")
  void deleteProfile() {
    // Arrange
    Integer id = 126;
    Profile profile = Profile.builder()
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();

    when(profileRepository.findById(anyInt())).thenReturn(Optional.of(profile));
    doNothing().when(profileRepository).deleteById(anyInt());

    // Act
    profileService.deleteProfile(id);

    // Assert
    verify(profileRepository, times(1)).deleteById(id);

  }


  @Test
  @DisplayName("deleteProfile - should throw RecordNotFoundException when profile is not found")
  void deleteProfile_DrinkNotFound_ThrowsException() {
    // Arrange
    Integer id = 999;
    when(profileRepository.findById(id)).thenReturn(Optional.empty());
    // Act & Assert
    assertThrows(RecordNotFoundException.class, () -> profileService.deleteProfile(id));
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn_notValidatedProfileFound - testing GET method for profile based on if he is logged in")
  void getProfileByUserLoggedIn_notValidatedProfileFound() {
    // Arrange
    User user = User.builder()
      .username("mjerta")
      .profile(null)
      .build();

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    // Act & Assert
    assertThrows(GeneralException.class, () -> profileService.getProfileByUserLoggedIn());
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn_authenticationNull - testing if GET method for profile based one authentication from SecurityContextHolder is null")
  void getProfileByUserLoggedIn_authenticationNull() {
    // Arrange
    SecurityContextHolder.clearContext();
    // Act & Assert: Check that the GeneralException is thrown
    assertThrows(GeneralException.class, () -> profileService.getProfileByUserLoggedIn());
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn_principalNotInstanceOfUserDetails - testing if GET method for profile based on if he is logged in when no user principal is found")
  void getProfileByUserLoggedIn_principalNotInstanceOfUserDetails() {
    // Arrange
    Authentication authentication = mock(Authentication.class);
    Object nonUserDetailsPrincipal = new Object();

    lenient().when(authentication.getPrincipal()).thenReturn(nonUserDetailsPrincipal);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Act & Assert
    assertThrows(GeneralException.class, () -> profileService.getProfileByUserLoggedIn());
  }

  @Test
  @DisplayName("updateProfile_succes - testing if PUT method for profile works")
  void updateProfile_succes() {
    // Arrange
    Integer id = 125;
    Profile expectedProfile = Profile.builder()
      .id(id)
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();
    User user = User.builder()
      .profile(expectedProfile)
      .orders(null)
      .username("mjerta")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_USER").username("mjerta").build()))
      .enabled(true)
      .build();

    expectedProfile = expectedProfile.toBuilder().
      user(user)
      .build();

    user = user.toBuilder().profile(expectedProfile).build();

    ProfileCompleteRequestDto requestDto = ProfileCompleteRequestDto.builder()
      .firstName("Pietje")
      .lastName("Postma")
      .phoneNumber("0612345679")
      .address("Groen de prinsterstraat 10")
      .points(200)
      .build();

    expectedProfile = expectedProfile.toBuilder()
      .firstName(requestDto.getFirstName())
      .lastName(requestDto.getLastName())
      .phoneNumber(requestDto.getPhoneNumber())
      .address(requestDto.getAddress())
      .points(requestDto.getPoints())
      .build();
    when(profileRepository.save(any(Profile.class))).thenReturn(expectedProfile);
    // Mock userRepository to return the user with the profile
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

    // Act
    Profile updatedProfile = profileService.updateProfile(requestDto);

    // Assert
    assertEquals("Pietje", updatedProfile.getFirstName());
    assertEquals("Postma", updatedProfile.getLastName());
    assertEquals("0612345679", updatedProfile.getPhoneNumber());
    assertEquals("Groen de prinsterstraat 10", updatedProfile.getAddress());
    assertEquals(200, updatedProfile.getPoints());
  }

  @Test
  @DisplayName("updateProfile_withNullValues - testing if PUT method works and also with null values")
  void updateProfile_withNullValues() {
    // Arrange
    Integer id = 125;
    Profile expectedProfile = Profile.builder()
      .id(id)
      .firstName("Maarten")
      .lastName("Postma")
      .phoneNumber("0612345678")
      .address("JanKlaaseStraat")
      .points(130)
      .build();
    User user = User.builder()
      .profile(expectedProfile)
      .orders(null)
      .username("mjerta")
      .password("pass")
      .authorities(Set.of(Authority.builder().authority("ROLE_USER").username("mjerta").build()))
      .enabled(true)
      .build();

    expectedProfile = expectedProfile.toBuilder().
      user(user)
      .build();

    user = user.toBuilder().profile(expectedProfile).build();

    ProfileCompleteRequestDto requestDto = ProfileCompleteRequestDto.builder()
      .firstName(null)
      .lastName(null)
      .phoneNumber(null)
      .address(null)
      .points(null)
      .build();

    expectedProfile = expectedProfile.toBuilder()
      .firstName(requestDto.getFirstName())
      .lastName(requestDto.getLastName())
      .phoneNumber(requestDto.getPhoneNumber())
      .address(requestDto.getAddress())
      .points(requestDto.getPoints())
      .build();
    when(profileRepository.save(any(Profile.class))).thenReturn(expectedProfile);
    // Mock userRepository to return the user with the profile
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

    // Act
    Profile updatedProfile = profileService.updateProfile(requestDto);

    // Assert
    assertNull(updatedProfile.getFirstName());
    assertNull(updatedProfile.getLastName());
    assertNull(updatedProfile.getPhoneNumber());
    assertNull(updatedProfile.getAddress());
    assertNull(updatedProfile.getPoints());
  }
}
