package nl.mpdev.hotel_california_backend.service;

import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

  @Mock
  ProfileRepository profileRepository;

  @Mock
  UserRepository userRepository;

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
  @DisplayName("getProfileByUserLoggedIn - testing if GET method for profile based on if he is logged in")
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
  @DisplayName("getProfileByUserLoggedIn - testing if GET method for profile based on if he is logged in")
  void getProfileByUserLoggedIn_notValidatedProfileFound() {
    // Arrange
    User user = User.builder()
      .username("mjerta")
      .profile(null)
      .build();

    // Mock userRepository to return the user with the profile
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    // Act & Assert
    assertThrows(GeneralException.class, () -> profileService.getProfileByUserLoggedIn());
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn - testing if GET method for profile based on if he is logged in")
  void getProfileByUserLoggedIn_authenticationNull() {
    // Arrange
    SecurityContextHolder.clearContext();
    // Act & Assert: Check that the GeneralException is thrown
    assertThrows(GeneralException.class, () -> profileService.getProfileByUserLoggedIn());
  }

  @Test
  @DisplayName("getProfileByUserLoggedIn - testing if GET method for profile based on if he is logged in")
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
  @DisplayName("getProfileByUserLoggedIn - testing if GET method for profile based on if he is logged in")
  void updateProfile() {
    // Arrange
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
      .firstName("Johan")
      .lastName("Postma")
      .phoneNumber("0612345679")
      .address("Groen de prinsterstraat 10")
      .points(200)
      .build();
    when(profileRepository.save(any(Profile.class))).thenReturn(expectedProfile);
    // Mock userRepository to return the user with the profile
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

    // Act
    Profile updatedProfile = profileService.updateProfile(requestDto);


    // Assert
    assertEquals("Johan", updatedProfile.getFirstName());
    assertEquals("Postma", updatedProfile.getLastName());
    assertEquals("0612345679", updatedProfile.getPhoneNumber());
    assertEquals("Groen de prinsterstraat 10", updatedProfile.getAddress());
    assertEquals(200, updatedProfile.getPoints());
  }

}
